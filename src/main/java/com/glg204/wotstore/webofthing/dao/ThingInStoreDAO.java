package com.glg204.wotstore.webofthing.dao;

import com.glg204.wotstore.webofthing.domain.ThingInStore;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThingInStoreDAO {

    Optional<ThingInStore> getThingInStoreById(Long id);

    List<Optional<ThingInStore>> getThingsInStore();

    Long save(ThingInStore thingInStore);

    Boolean delete(Long id);
}
