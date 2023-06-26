package com.glg204.wotstore.client.dao;

import com.glg204.wotstore.client.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class ClientDAOImpl implements ClientDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;


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
}
