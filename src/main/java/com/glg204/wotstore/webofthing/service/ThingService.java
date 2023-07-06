package com.glg204.wotstore.webofthing.service;

import com.glg204.wotstore.webofthing.dto.ThingDTO;
import com.glg204.wotstore.webofthing.dto.ThingTypeDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ThingService {

    List<ThingDTO> getThings();

    Optional<ThingDTO> getByTitle(String title);

    List<ThingTypeDTO> getThingsType();
}
