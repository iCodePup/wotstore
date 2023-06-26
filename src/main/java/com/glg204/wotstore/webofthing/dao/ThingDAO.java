package com.glg204.wotstore.webofthing.dao;

import io.webthings.webthing.Property;
import io.webthings.webthing.Thing;
import io.webthings.webthing.Value;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ThingDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Thing getByTitle(String title) {
        String sqlGetThing = "select * from thing where title = ?";
        try {
            Thing thing = jdbcTemplate.queryForObject(sqlGetThing, new Object[]{title}, (rs, rowNum) ->
                    new Thing(rs.getString("id"),
                            rs.getString("title"),
                            new JSONArray(rs.getString("typeAsJson")),
                            rs.getString("description"))

            );
            getThingProperties(thing).forEach(thing::addProperty);
            return thing;
        } catch (Exception e) {
            e.printStackTrace(); //todo
        }
        return null;
    }

    private List<Property> getThingProperties(Thing thing) {
        String sqlGetProperties = "select * from thing_property WHERE thingid = ?";
        Integer thingId = Integer.parseInt(thing.getId());
        List<Property> properties = jdbcTemplate.queryForList(sqlGetProperties, new Object[]{thingId}).stream().map(row -> {
            //TODO construct objet value
            Property p = new Property(thing, (String) row.get("name"), (Value) row.get("value"), new JSONObject(row.get("metadataasjson").toString()));
            return p;
        }).toList();
        return properties;
    }
}
