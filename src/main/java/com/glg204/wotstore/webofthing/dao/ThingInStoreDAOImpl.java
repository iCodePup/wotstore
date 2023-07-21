package com.glg204.wotstore.webofthing.dao;

import com.glg204.wotstore.client.dao.ClientDAO;
import com.glg204.wotstore.client.domain.Client;
import com.glg204.wotstore.webofthing.domain.ThingInStore;
import com.glg204.wotstore.webofthing.domain.ThingType;
import io.webthings.webthing.Thing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ThingInStoreDAOImpl implements ThingInStoreDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ThingTypeDAO thingTypeDAO;

    @Autowired
    ClientDAO clientDAO;

    @Override
    public Optional<ThingInStore> getThingInStoreById(Long id) {
        String sqlGetThingInStore = "select * from thing_in_store where id = ?";
        try {
            Optional<ThingInStore> thingInStore = jdbcTemplate.queryForObject(sqlGetThingInStore, new Object[]{id}, (rs, rowNum) -> {
                Optional<ThingType> optionalThing = thingTypeDAO.getById(Long.parseLong(rs.getString("thingtypeid")));
                Long aId = rs.getLong("id");
                String name = rs.getString("name");
                String desc = rs.getString("description");
                Double prix = rs.getDouble("prix");
                boolean started = rs.getBoolean("started");
                return optionalThing.map(thingType -> {
                    Thing thing = new Thing(String.valueOf(aId), name, thingType.getTypeAsJson(), desc);
                    thingType.getProperties().forEach((key, value) -> {
                        thing.addProperty(value);
                    });
                    return new ThingInStore(
                            aId, name, desc, prix, started,
                            thingType, thing);
                });
            });
            return thingInStore;
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Optional<ThingInStore>> getThingsInStore() {
        String sqlGetThingInStore = "select * from thing_in_store";
        try {
            List<Optional<ThingInStore>> thingsInStore = jdbcTemplate.queryForList(sqlGetThingInStore).stream().map(row -> {
                Optional<ThingType> optionalThing = thingTypeDAO.getById(Long.parseLong(row.get("thingtypeid").toString()));
                return optionalThing.map(thingType -> {
                    Thing thing = new Thing(row.get("id").toString(), row.get("name").toString(), thingType.getTypeAsJson(), row.get("description").toString());
                    thingType.getProperties().forEach((key, value) -> {
                        thing.addProperty(value);
                    });
                    ThingInStore t = new ThingInStore(
                            Long.parseLong(row.get("id").toString()),
                            String.valueOf(row.get("name")),
                            String.valueOf(row.get("description")),
                            Double.parseDouble(row.get("prix").toString()),
                            Boolean.parseBoolean(row.get("started").toString()),
                            thingType, thing);
                    if (row.get("clientid") != null) {
                        Optional<Client> optionalClient = clientDAO.getById(Long.parseLong(row.get("clientid").toString()));
                        if (optionalClient.isPresent()) {
                            t.setClient(optionalClient.get());
                        }
                    }
                    return t;
                });

            }).toList();
            return thingsInStore;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public Long save(ThingInStore thingInStore) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into thing_in_store (id, name, description, prix, thingtypeid)" +
                            " values(?, ?, ?, ?, ?)" +
                            " on conflict (id) do update" +
                            " set name=EXCLUDED.name," +
                            " description=EXCLUDED.description," +
                            " prix=EXCLUDED.prix," +
                            " thingtypeid=EXCLUDED.thingtypeid");
            ps.setLong(1, thingInStore.getId());
            ps.setString(2, thingInStore.getName());
            ps.setString(3, thingInStore.getDescription());
            ps.setDouble(4, thingInStore.getPrix());
            ps.setInt(5, Integer.parseInt(thingInStore.getThingType().getId()));
            return ps;
        });
        return thingInStore.getId();
    }

    @Override
    public Boolean delete(Long id) {
        String sql = "delete from thing_in_store where id = ?";
        Object[] args = new Object[]{id};
        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public List<Optional<ThingInStore>> getClientThingsInStore(Client client) {
        String sqlGetThingInStore = "select * from thing_in_store where clientid = ?";
        try {
            List<Optional<ThingInStore>> thingsInStore = jdbcTemplate.queryForList(sqlGetThingInStore, new Object[]{client.getId()}).stream().map(row -> {
                Optional<ThingType> optionalThing = thingTypeDAO.getById(Long.parseLong(row.get("thingtypeid").toString()));
                return optionalThing.map(thingType -> {
                    Thing thing = new Thing(row.get("id").toString(), row.get("name").toString(), thingType.getTypeAsJson(), row.get("description").toString());
                    thingType.getProperties().forEach((key, value) -> {
                        thing.addProperty(value);
                    });
                    ThingInStore t = new ThingInStore(
                            Long.parseLong(row.get("id").toString()),
                            String.valueOf(row.get("name")),
                            String.valueOf(row.get("description")),
                            Double.parseDouble(row.get("prix").toString()),
                            Boolean.parseBoolean(row.get("started").toString()),
                            thingType, thing);
                    t.setClient(client);
                    return t;
                });
            }).toList();
            return thingsInStore;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
