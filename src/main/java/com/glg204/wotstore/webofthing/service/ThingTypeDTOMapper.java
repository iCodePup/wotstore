package com.glg204.wotstore.webofthing.service;


import com.glg204.wotstore.webofthing.dto.ThingTypeDTO;
import io.webthings.webthing.Thing;
import org.springframework.stereotype.Service;

@Service
public class ThingTypeDTOMapper {

    ThingTypeDTOMapper() {}

    public static ThingTypeDTO toDTO(Thing thing) {
        return new ThingTypeDTO(thing.getId(), thing.getId(), thing.getTitle());
    }
}
