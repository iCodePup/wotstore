package com.glg204.wotstore.authentification.service;

import com.glg204.wotstore.authentification.domain.WOTUser;
import com.glg204.wotstore.client.dto.ClientDTO;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public interface WOTUserService {

    public Optional<WOTUser> getUserByUsername(String username);

    public boolean existsByEmail(String email);

    public WOTUser save(PasswordEncoder passwordEncoder,ClientDTO clientDTO);
}
