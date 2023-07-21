package com.glg204.wotstore.webofthing.service;

import com.glg204.wotstore.webofthing.dto.ThingTypeDTO;
import com.glg204.wotstore.webofthing.dto.ThingTypeSelectDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ThingTypeService {

    Optional<ThingTypeDTO> getByTitle(String title);

    List<ThingTypeSelectDTO> getThingsType();

    public List<ThingTypeDTO> getThings();
}
