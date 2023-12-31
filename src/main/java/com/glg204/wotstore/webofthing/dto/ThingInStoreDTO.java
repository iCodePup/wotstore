package com.glg204.wotstore.webofthing.dto;

import com.glg204.wotstore.client.dto.ClientDTO;
import jakarta.validation.constraints.NotNull;

public class ThingInStoreDTO {

    private Long id;

    @NotNull
    private String thingTypeId;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Double prix;

    private boolean started;

    private ClientDTO client;

    public ThingInStoreDTO() {
    }

    public ThingInStoreDTO(Long id, String thingTypeId, String name, String description, Double prix, boolean started) {
        this.id = id;
        this.thingTypeId = thingTypeId;
        this.name = name;
        this.description = description;
        this.prix = prix;
        this.started = started;
    }


    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThingTypeId() {
        return thingTypeId;
    }

    public void setThingTypeId(String thingTypeId) {
        this.thingTypeId = thingTypeId;
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

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
