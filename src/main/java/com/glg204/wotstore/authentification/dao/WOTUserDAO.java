package com.glg204.wotstore.authentification.dao;


import com.glg204.wotstore.authentification.domain.WOTUser;

import java.util.Optional;

public interface WOTUserDAO {
    Optional<WOTUser> findByEmail(String email);

    boolean existsByEmail(String email);

    WOTUser save(WOTUser wotUser);
}
