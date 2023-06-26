package com.glg204.wotstore.authentification.service;

import com.glg204.wotstore.authentification.domain.WOTUser;
import com.glg204.wotstore.authentification.domain.WOTUserRole;
import com.glg204.wotstore.client.dto.ClientDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class WOTUserDTOMapper {

    public WOTUser fromCreationDTO(PasswordEncoder passwordEncoder, ClientDTO clientDTO) {
        WOTUser wotUser = new WOTUser(clientDTO.getEmail(),
                clientDTO.getFirstName(),
                clientDTO.getLastName(),
                passwordEncoder.encode(clientDTO.getPassword()),
                WOTUserRole.CLIENT);
        return wotUser;
    }
}
