package com.glg204.wotstore.webofthing.service;

import com.glg204.wotstore.webofthing.dao.ThingInStoreDAO;
import com.glg204.wotstore.webofthing.dao.ThingTypeDAO;
import com.glg204.wotstore.webofthing.domain.ThingType;
import com.glg204.wotstore.webofthing.dto.ThingInStoreDTO;
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
    private ThingTypeDAO thingTypeDAO;

    @Autowired
    private ThingInStoreDTOMapper thingInStoreDTOMapper;

    @Autowired
    private WebThingServerService webThingServerService;

    @Override
    public List<ThingInStoreDTO> getThingsInStore() {
        return thingInStoreDAO.getThingsInStore().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(thingInStore -> thingInStoreDTOMapper.toDTO(thingInStore)).collect(Collectors.toList());
    }

    @Override
    public Boolean delete(Long id) {
        webThingServerService.deleteThing(id);
        return thingInStoreDAO.delete(id);
    }

    @Override
    public Optional<Long> save(ThingInStoreDTO thingInStoreDTO) {
        Optional<ThingType> optionalThingType = thingTypeDAO.getById(Long.parseLong(thingInStoreDTO.getThingTypeId()));
        return optionalThingType.map(thingType -> {
            Long id = thingInStoreDAO.save(thingInStoreDTOMapper.fromCreationDTO(thingInStoreDTO, thingType));
            return Optional.of(id);
        }).orElseGet(() -> Optional.empty());
    }
}
