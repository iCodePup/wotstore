package com.glg204.wotstore.webofthing.service;


import com.glg204.wotstore.webofthing.domain.ThingType;
import com.glg204.wotstore.webofthing.dto.ThingTypeSelectDTO;
import org.springframework.stereotype.Service;

@Service
public class ThingTypeSelectDTOMapper {

    ThingTypeSelectDTOMapper() {}

    public ThingTypeSelectDTO toDTO(ThingType thing) {
        return new ThingTypeSelectDTO(thing.getId(), thing.getId(), thing.getTitle());
    }
}
