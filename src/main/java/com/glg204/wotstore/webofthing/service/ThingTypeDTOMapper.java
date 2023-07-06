package com.glg204.wotstore.webofthing.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glg204.wotstore.webofthing.dto.ThingDTO;
import com.glg204.wotstore.webofthing.dto.ThingTypeDTO;
import io.webthings.webthing.Thing;
import org.springframework.stereotype.Service;

@Service
public class ThingTypeDTOMapper {

    ThingTypeDTOMapper() {
    }

    public static ThingTypeDTO toDTO(Thing thing) {
        return new ThingTypeDTO(thing.getId(), thing.getId(), thing.getTitle());
    }

}
