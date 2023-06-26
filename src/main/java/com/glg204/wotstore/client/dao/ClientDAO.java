package com.glg204.wotstore.client.dao;

import com.glg204.wotstore.client.domain.Client;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDAO {
    Integer save(Client client);
}
