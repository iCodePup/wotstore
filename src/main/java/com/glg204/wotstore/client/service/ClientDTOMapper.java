package com.glg204.wotstore.client.service;

import com.glg204.wotstore.authentification.domain.WOTUser;
import com.glg204.wotstore.client.domain.Client;
import com.glg204.wotstore.client.dto.ClientDTO;
import org.springframework.stereotype.Service;

@Service
public class ClientDTOMapper {


    public Client fromCreationDTO(ClientDTO clientDTO, WOTUser wotUser) {
        Client client = new Client(wotUser);
        fillFromDTO(client, clientDTO);
        return client;
    }

    private void fillFromDTO(Client client, ClientDTO clientDTO) {
        client.setAddress(clientDTO.getAddress());
        client.setTelephone(clientDTO.getTelephone());
    }

    public ClientDTO toDTO(Client client) {
      return new ClientDTO(client.getWotUser().getEmail(),
              client.getWotUser().getFirstName(),
              client.getWotUser().getLastName(),
              client.getTelephone(),
              client.getAddress());
    }
}

