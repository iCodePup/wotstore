package com.glg204.wotstore.webofthing.dto;

import com.fasterxml.jackson.databind.JsonNode;


public class ThingDTO {

    private String id;
    private String title;
    private JsonNode type;
    private String description;
    private JsonNode properties;

    public ThingDTO(String id, String title, JsonNode type, String description, JsonNode properties) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.description = description;
        this.properties = properties;

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

    public JsonNode getType() {
        return type;
    }

    public void setType(JsonNode type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JsonNode getProperties() {
        return properties;
    }

    public void setProperties(JsonNode properties) {
        this.properties = properties;
    }
}
