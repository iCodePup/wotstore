package com.glg204.wotstore.webofthing.service;

import com.glg204.wotstore.webofthing.dao.ThingTypeDAO;
import com.glg204.wotstore.webofthing.dto.ThingTypeDTO;
import com.glg204.wotstore.webofthing.dto.ThingTypeSelectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThingTypeServiceImpl implements ThingTypeService {

    @Autowired
    ThingTypeDAO thingTypeDAO;

    @Autowired
    ThingTypeDTOMapper thingTypeDTOMapper;

    @Autowired
    ThingTypeSelectDTOMapper thingTypeSelectDTOMapper;

    @Override
    public Optional<ThingTypeDTO> getByTitle(String title) {
        return thingTypeDAO.getByTitle(title).map(thing -> thingTypeDTOMapper.toDTO(thing));
    }

    @Override
    public List<ThingTypeSelectDTO> getThingsType() {
        return thingTypeDAO.getThingTypes().stream().map(thing -> thingTypeSelectDTOMapper.toDTO(thing)).toList();
    }

    @Override
    public List<ThingTypeDTO> getThings() {
        return thingTypeDAO.getThingTypes().stream().map(thing -> thingTypeDTOMapper.toDTO(thing)).toList();
    }
}
