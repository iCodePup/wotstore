package com.glg204.wotstore.webofthing.dao;

import com.glg204.wotstore.webofthing.domain.ThingType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThingTypeDAO {

    List<ThingType> getThingTypes();

    Optional<ThingType> getByTitle(String title);

    Optional<ThingType> getById(Long thingTypeId);
}
