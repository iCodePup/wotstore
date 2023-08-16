package com.glg204.wotstore.domain;

import io.webthings.webthing.Property;
import io.webthings.webthing.Thing;
import org.json.JSONArray;

public class ThingMock extends Thing {
    public ThingMock(String id, String title, JSONArray type, String description) {
        super(id, title, type, description);
    }

    @Override
    public void addProperty(Property property) {
        super.addProperty(property);
    }
}