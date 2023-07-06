package com.glg204.wotstore.webofthing.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class ThingTypeDTO {

    private String key;
    private String value;
    private String label;

    public ThingTypeDTO(String key, String value, String label) {
        this.key = key;
        this.value = value;
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
