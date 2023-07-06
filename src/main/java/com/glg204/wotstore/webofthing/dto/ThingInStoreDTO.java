package com.glg204.wotstore.webofthing.dto;

public class ThingInStoreDTO {

    private Long id;
    private String thingId;
    private String name;
    private String description;
    private Double prix;

    public ThingInStoreDTO() {}

    public ThingInStoreDTO(Long id, String thingId, String name, String description, Double prix) {
        this.id = id;
        this.thingId = thingId;
        this.name = name;
        this.description = description;
        this.prix = prix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThingId() {
        return thingId;
    }

    public void setThingId(String thingId) {
        this.thingId = thingId;
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
}
