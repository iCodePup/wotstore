package com.glg204.wotstore.client.dao;

import com.glg204.wotstore.authentification.dao.WOTUserDAO;
import com.glg204.wotstore.authentification.domain.WOTUser;
import com.glg204.wotstore.client.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientDAOImpl implements ClientDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    WOTUserDAO wotUserDAO;

    @Override
    public Integer save(Client client) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into client (email,telephone,address) values(?,?,?)", new String[]{"id"});
            ps.setString(1, client.getWotUser().getEmail());
            ps.setString(2, client.getTelephone());
            ps.setString(3, client.getAddress());
            return ps;

        }, keyHolder);

        return (Integer) keyHolder.getKey();

    }

    @Override
    public List<Optional<Client>> getClients() {
        String sqlGetThing = "select * from client";
        try {
            return jdbcTemplate.queryForList(sqlGetThing).stream().map(row -> {
                return wotUserDAO.findByEmail(String.valueOf(row.get("email"))).map(wtUser -> {
                    WOTUser wotUser = wotUserDAO.findByEmail(String.valueOf(row.get("email"))).get();
                    Client c = new Client(
                            wotUser,
                            String.valueOf(row.get("telephone")),
                            String.valueOf(row.get("address")));
                    return Optional.of(c);
                }).orElseGet(() -> Optional.empty());

            }).toList();
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Client> getClientByEmail(String email) {
        try {
            Client client = jdbcTemplate.queryForObject("select * from client where email = ?",
                    BeanPropertyRowMapper.newInstance(Client.class), email);
            return Optional.of(client);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean setClientToThingInStore(Long thingInStoreId, Long clientId) {
        String sql = "UPDATE thing_in_store SET clientid = ? WHERE id = ?";
        Object[] args = new Object[]{clientId, thingInStoreId};
        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public Optional<Client> getById(long clientid) {
        try {
            Client client = jdbcTemplate.queryForObject("select * from client where id = ?",
                    BeanPropertyRowMapper.newInstance(Client.class), clientid);
            return Optional.of(client);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

}
