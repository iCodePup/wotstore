package com.glg204.wotstore.client.service;

import com.glg204.wotstore.authentification.domain.WOTUser;
import com.glg204.wotstore.authentification.exception.EmailAlreadyExistsException;
import com.glg204.wotstore.authentification.service.WOTUserService;
import com.glg204.wotstore.client.dao.ClientDAO;
import com.glg204.wotstore.client.domain.Client;
import com.glg204.wotstore.client.dto.ClientDTO;
import com.glg204.wotstore.webofthing.dao.ThingInStoreDAO;
import com.glg204.wotstore.webofthing.domain.ThingInStore;
import com.glg204.wotstore.webofthing.dto.ThingInStoreDTO;
import com.glg204.wotstore.webofthing.service.ThingInStoreDTOMapper;
import com.glg204.wotstore.webofthing.service.WebThingServerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Validated
public class ClientServiceImpl implements ClientService {

    @Autowired
    WOTUserService wotUserService;

    @Autowired
    ClientDAO clientDAO;

    @Autowired
    ThingInStoreDAO thingInStoreDAO;

    @Autowired
    ClientDTOMapper clientDTOMapper;

    @Autowired
    ThingInStoreDTOMapper thingInStoreDTOMapper;

    @Autowired
    WebThingServerService webThingServerService;

    @Override
    public Integer save(PasswordEncoder passwordEncoder, @Valid ClientDTO clientDTO) throws EmailAlreadyExistsException {
        if (wotUserService.existsByEmail(clientDTO.getEmail())) {
            throw new EmailAlreadyExistsException();
        } else {

            WOTUser wotUser = wotUserService.save(passwordEncoder, clientDTO);
            // Creates the corresponding user.
            return clientDAO.save(clientDTOMapper.fromCreationDTO(clientDTO, wotUser));
        }
    }

    @Override
    public List<ClientDTO> getClients() {
        return clientDAO.getClients().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(client -> clientDTOMapper.toDTO(client)).collect(Collectors.toList());
    }

    @Override
    public boolean purchaseThingInStore(Principal p, Long thingInStoreId) {
        Optional<Client> client = clientDAO.getClientByEmail(p.getName());
        return client.filter(value -> clientDAO.setClientToThingInStore(thingInStoreId, value.getId())).isPresent();
    }

    @Override
    public List<ThingInStoreDTO> getClientThingsInStore(Principal p) {
        Optional<Client> client = clientDAO.getClientByEmail(p.getName());
        return client.map(value -> thingInStoreDAO.getClientThingsInStore(value).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(thingInStore -> thingInStoreDTOMapper.toDTO(thingInStore)).collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @Override
    public boolean startThingInStore(Long thingInStoreId) {
        Optional<ThingInStore> thingInStore = thingInStoreDAO.getThingInStoreById(thingInStoreId);
        if (thingInStore.isPresent()) {
            if (webThingServerService.startThing(thingInStore.get().getThing())) {
                return clientDAO.setStatusToThingInStore(thingInStoreId, true);
            }
        }
        return false;
    }

    @Override
    public boolean stopThingInStore(Long thingInStoreId) {
        Optional<ThingInStore> thingInStore = thingInStoreDAO.getThingInStoreById(thingInStoreId);
        if (thingInStore.isPresent()) {
            if (webThingServerService.stopThing(thingInStore.get())) {
                return clientDAO.setStatusToThingInStore(thingInStoreId, false);
            }
        }
        return false;
    }
}
