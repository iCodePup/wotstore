package com.glg204.wotstore.webofthing.domain;

import io.webthings.webthing.Property;
import io.webthings.webthing.Thing;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class ThingType {

    private String id;

    private String title;

    private JSONArray typeAsJson;

    private String description;

    private Map<String, Property> properties = new HashMap<>();

    private Thing thing;

    public ThingType(String id, String title, JSONArray typeAsJson, String description) {
        this.id = id;
        this.title = title;
        this.typeAsJson = typeAsJson;
        this.description = description;
    }

    public void addProperty(Property property) {
        property.setHrefPrefix("");
        this.properties.put(property.getName(), property);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JSONArray getTypeAsJson() {
        return typeAsJson;
    }

    public void setTypeAsJson(JSONArray typeAsJson) {
        this.typeAsJson = typeAsJson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Property> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Property> properties) {
        this.properties = properties;
    }

    public Thing getThing() {
        return thing;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
    }
}
