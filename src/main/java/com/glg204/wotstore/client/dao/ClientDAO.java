package com.glg204.wotstore.client.dao;

import com.glg204.wotstore.client.domain.Client;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientDAO {
    Integer save(Client client);

    List<Optional<Client>> getClients();

    Optional<Client> getClientByEmail(String name);

    boolean setClientToThingInStore(Long id, Long id1);

    Optional<Client> getById(long clientid);
}
