package com.glg204.wotstore.webofthing.service;

import com.glg204.wotstore.webofthing.dto.ThingInStoreDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ThingInStoreService {

    Optional<Long> save(ThingInStoreDTO thingInStoreDTO);

    List<ThingInStoreDTO> getThingsInStore();

    Boolean delete(Long id);
}
