package com.glg204.wotstore.webofthing.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glg204.wotstore.webofthing.dto.ThingDTO;
import io.webthings.webthing.Thing;
import org.springframework.stereotype.Service;

@Service
public class ThingDTOMapper {

    ThingDTOMapper() {
    }

    public static ThingDTO toDTO(Thing thing) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode type = mapper.readTree(thing.getType().toString());
            JsonNode properties = mapper.readTree(thing.getProperties().toString());
            return new ThingDTO(thing.getId(), thing.getTitle(), type, thing.getDescription(), properties);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
