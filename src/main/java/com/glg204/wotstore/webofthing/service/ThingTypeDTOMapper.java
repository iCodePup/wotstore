package com.glg204.wotstore.webofthing.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glg204.wotstore.webofthing.domain.ThingType;
import com.glg204.wotstore.webofthing.dto.ThingTypeDTO;
import org.springframework.stereotype.Service;

@Service
public class ThingTypeDTOMapper {

    public static ThingTypeDTO toDTO(ThingType thing) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode type = mapper.readTree(thing.getTypeAsJson().toString());
            JsonNode properties = mapper.readTree(thing.getProperties().toString());
            return new ThingTypeDTO(thing.getId(), thing.getTitle(), type, thing.getDescription(), properties);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
