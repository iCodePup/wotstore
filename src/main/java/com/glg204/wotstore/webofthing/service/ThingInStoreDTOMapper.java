package com.glg204.wotstore.webofthing.service;

import com.glg204.wotstore.webofthing.domain.ThingInStore;
import com.glg204.wotstore.webofthing.dto.ThingInStoreDTO;
import io.webthings.webthing.Thing;
import org.springframework.stereotype.Service;

@Service
public class ThingInStoreDTOMapper {

    public ThingInStore fromCreationDTO(ThingInStoreDTO thingInStoreDTO, Thing thing) {
        return new ThingInStore(thingInStoreDTO.getId(),
                thingInStoreDTO.getName(),
                thingInStoreDTO.getDescription(),
                thingInStoreDTO.getPrix(),
                thing);
    }

    public ThingInStoreDTO toDTO(ThingInStore thingInStore) {
        return new ThingInStoreDTO(
                thingInStore.getId(),
                thingInStore.getThing().getId(),
                thingInStore.getName(),
                thingInStore.getDescription(),
                thingInStore.getPrix()
        );
    }
}
