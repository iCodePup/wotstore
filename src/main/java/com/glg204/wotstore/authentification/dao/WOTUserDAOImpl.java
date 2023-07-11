package com.glg204.wotstore.authentification.dao;


import com.glg204.wotstore.authentification.domain.WOTUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
public class WOTUserDAOImpl implements WOTUserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<WOTUser> findByEmail(String email) {
        try {
            WOTUser wotUser = jdbcTemplate.queryForObject("select * from wot_user where email = ?",
                    BeanPropertyRowMapper.newInstance(WOTUser.class), email);
            return Optional.of(wotUser);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    @Override
    public WOTUser save(WOTUser wotUser) {

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into wot_user (email,firstname,lastname,password,role) values(?,?,?,?,?)");
            ps.setString(1, wotUser.getEmail());
            ps.setString(2, wotUser.getFirstName());
            ps.setString(3, wotUser.getLastName());
            ps.setString(4, wotUser.getPassword());
            ps.setString(5, wotUser.getRole().name());
            return ps;
        });
        return wotUser;


    }
}
