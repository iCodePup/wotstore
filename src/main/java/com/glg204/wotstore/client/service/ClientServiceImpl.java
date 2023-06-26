package com.glg204.wotstore.client.service;

import com.glg204.wotstore.authentification.domain.WOTUser;
import com.glg204.wotstore.authentification.exception.EmailAlreadyExistsException;
import com.glg204.wotstore.authentification.service.WOTUserService;
import com.glg204.wotstore.client.dao.ClientDAO;
import com.glg204.wotstore.client.dto.ClientDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Service
@Validated
public class ClientServiceImpl implements ClientService {

    @Autowired
    WOTUserService wotUserService;

    @Autowired
    ClientDAO clientDAO;

    @Autowired
    ClientDTOMapper clientDTOMapper;

    @Override
    public Integer save(PasswordEncoder passwordEncoder, @Valid ClientDTO clientDTO) throws EmailAlreadyExistsException {
        if (wotUserService.existsByEmail(clientDTO.getEmail())) {
            throw new EmailAlreadyExistsException();
        } else {

            WOTUser wotUser = wotUserService.save(passwordEncoder,clientDTO);
            // Creates the corresponding user.
            return clientDAO.save(clientDTOMapper.fromCreationDTO(clientDTO, wotUser));
        }
    }

}