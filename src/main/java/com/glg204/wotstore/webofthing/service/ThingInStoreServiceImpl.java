package com.glg204.wotstore.webofthing.service;

import com.glg204.wotstore.webofthing.dao.ThingDAO;
import com.glg204.wotstore.webofthing.dao.ThingInStoreDAO;
import com.glg204.wotstore.webofthing.dto.ThingInStoreDTO;
import io.webthings.webthing.Thing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ThingInStoreServiceImpl implements ThingInStoreService {

    @Autowired
    private ThingInStoreDAO thingInStoreDAO;

    @Autowired
    private ThingDAO thingDAO;

    @Autowired
    private ThingInStoreDTOMapper thingInStoreDTOMapper;

    @Override
    public List<ThingInStoreDTO> getThingsInStore() {
        return thingInStoreDAO.getThingsInStore().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(thingInStore -> thingInStoreDTOMapper.toDTO(thingInStore)).collect(Collectors.toList());
    }

    @Override
    public Boolean delete(Long id) {
        return thingInStoreDAO.delete(id);
    }

    @Override
    public Optional<Long> save(ThingInStoreDTO thingInStoreDTO) {
        Optional<Thing> optionalThing = thingDAO.getById(Long.parseLong(thingInStoreDTO.getThingId()));
        return optionalThing.map(thing -> {
            Long id = thingInStoreDAO.save(thingInStoreDTOMapper.fromCreationDTO(thingInStoreDTO, thing));
            return Optional.of(id);
        }).orElseGet(() -> Optional.empty());
    }
}
