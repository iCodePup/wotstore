package com.glg204.wotstore.webofthing.domain;

import com.glg204.wotstore.client.domain.Client;
import io.webthings.webthing.Thing;

public class ThingInStore {

    private Long id;

    private String name;

    private String description;

    private Double prix;

    private Thing thing;

    private Client client;

    public ThingInStore(Long id, String name, String description, Double prix, Thing thing) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prix = prix;
        this.thing = thing;
    }

    public ThingInStore(Long id, String name, String description, Double prix, Thing thing,Client client) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prix = prix;
        this.thing = thing;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Thing getThing() {
        return thing;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
