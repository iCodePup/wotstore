package com.glg204.wotstore.webofthing.dao;

import io.webthings.webthing.Thing;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThingDAO {

    List<Thing> getThings();

    Optional<Thing> getByTitle(String title);

    Optional<Thing> getById(Long thingId);
}
