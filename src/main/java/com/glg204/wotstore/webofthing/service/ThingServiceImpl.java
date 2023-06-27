package com.glg204.wotstore.webofthing.service;

import com.glg204.wotstore.webofthing.dao.ThingDAO;
import com.glg204.wotstore.webofthing.dto.ThingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThingServiceImpl implements ThingService {

    @Autowired
    ThingDAO thingDAO;

    @Autowired
    ThingDTOMapper thingDTOMapper;

    @Override
    public Optional<ThingDTO> getByTitle(String title) {
        return thingDAO.getByTitle(title).map(thing -> ThingDTOMapper.toDTO(thing));
    }

    @Override
    public Optional<List<ThingDTO>> getThings() {
        return thingDAO.getThings().map(things -> things.stream().map(thing -> ThingDTOMapper.toDTO(thing)).toList());
    }
}
