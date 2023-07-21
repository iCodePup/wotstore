package com.glg204.wotstore.webofthing.dao;

import com.glg204.wotstore.webofthing.domain.ThingType;
import io.webthings.webthing.Property;
import io.webthings.webthing.Thing;
import io.webthings.webthing.Value;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ThingTypeDAOImpl implements ThingTypeDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ThingType> getThingTypes() {
        String sqlGetThing = "select * from thing_type";
        try {
            List<ThingType> things = jdbcTemplate.queryForList(sqlGetThing).stream().map(row -> {
                ThingType t = new ThingType(String.valueOf(row.get("id")),
                        String.valueOf(row.get("title")),
                        new JSONArray(row.get("typeAsJson").toString()),
                        String.valueOf(row.get("description")));
                getThingProperties(t).forEach(t::addProperty);
                return t;
            }).toList();
            return things;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public Optional<ThingType> getByTitle(String title) {
        String sqlGetThing = "select * from thing_type where title = ?";
        try {
            ThingType thing = jdbcTemplate.queryForObject(sqlGetThing, new Object[]{title}, (rs, rowNum) ->
                    new ThingType(rs.getString("id"),
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

    @Override
    public Optional<ThingType> getById(Long thingTypeId) {
        String sqlGetThing = "select * from thing_type where id = ?";
        try {
            ThingType thing = jdbcTemplate.queryForObject(sqlGetThing, new Object[]{thingTypeId}, (rs, rowNum) ->
                    new ThingType(String.valueOf(rs.getInt("id")),
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

    private List<Property> getThingProperties(ThingType thingType) {
        String sqlGetProperties = "select * from thing_property where thingtypeid = ?";
        Integer thingTypeId = Integer.parseInt(thingType.getId());
        List<Property> properties = jdbcTemplate.queryForList(sqlGetProperties, new Object[]{thingTypeId}).stream().map(row -> {
            Thing thing = new Thing(thingType.getId(), thingType.getTitle(), thingType.getTypeAsJson(), thingType.getDescription());
            thingType.getProperties().forEach((key, value) -> {
                thing.addProperty(value);
            });
            Property p = new Property(thing, String.valueOf(row.get("name")), new Value<>(row.get("value")), new JSONObject(row.get("metadataasjson").toString()));
            return p;
        }).toList();
        return properties;
    }
}
