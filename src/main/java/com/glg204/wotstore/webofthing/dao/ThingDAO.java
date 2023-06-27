package com.glg204.wotstore.webofthing.dao;

import io.webthings.webthing.Property;
import io.webthings.webthing.Thing;
import io.webthings.webthing.Value;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ThingDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Optional<List<Thing>> getThings() {
        String sqlGetThing = "select * from thing";
        try {
            List<Thing> things = jdbcTemplate.queryForList(sqlGetThing).stream().map(row -> {
                Thing t = new Thing(String.valueOf(row.get("id")),
                        String.valueOf(row.get("title")),
                        new JSONArray(row.get("typeAsJson").toString()),
                        String.valueOf(row.get("description")));
                getThingProperties(t).forEach(t::addProperty);
                return t;
            }).toList();
            return Optional.of(things);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Thing> getByTitle(String title) {
        String sqlGetThing = "select * from thing where title = ?";
        try {
            Thing thing = jdbcTemplate.queryForObject(sqlGetThing, new Object[]{title}, (rs, rowNum) ->
                    new Thing(rs.getString("id"),
                            rs.getString("title"),
                            new JSONArray(rs.getString("typeAsJson")),
                            rs.getString("description"))

            );
            getThingProperties(thing).forEach(thing::addProperty);
            return Optional.of(thing);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private List<Property> getThingProperties(Thing thing) {
        String sqlGetProperties = "select * from thing_property where thingid = ?";
        Integer thingId = Integer.parseInt(thing.getId());
        List<Property> properties = jdbcTemplate.queryForList(sqlGetProperties, new Object[]{thingId}).stream().map(row -> {
            Property p = new Property(thing, String.valueOf(row.get("name")), new Value<>(row.get("value")), new JSONObject(row.get("metadataasjson").toString()));
            return p;
        }).toList();
        return properties;
    }


}
