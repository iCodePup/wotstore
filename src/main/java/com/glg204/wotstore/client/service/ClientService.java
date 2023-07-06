package com.glg204.wotstore.client.service;

import com.glg204.wotstore.authentification.domain.WOTUser;
import com.glg204.wotstore.authentification.exception.EmailAlreadyExistsException;
import com.glg204.wotstore.client.dto.ClientDTO;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;


@Service
@Validated
public interface ClientService {

    public Integer save(PasswordEncoder passwordEncoder, @Valid ClientDTO clientDTO) throws EmailAlreadyExistsException;

    List<ClientDTO> getClients();
}
