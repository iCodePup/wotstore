package com.glg204.wotstore.webofthing.service;

import com.glg204.wotstore.client.domain.Client;
import com.glg204.wotstore.client.dto.ClientDTO;
import com.glg204.wotstore.client.service.ClientDTOMapper;
import com.glg204.wotstore.webofthing.domain.ThingInStore;
import com.glg204.wotstore.webofthing.dto.ThingInStoreDTO;
import io.webthings.webthing.Thing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThingInStoreDTOMapper {

    @Autowired
    ClientDTOMapper clientDTOMapper;

    public ThingInStore fromCreationDTO(ThingInStoreDTO thingInStoreDTO, Thing thing) {
        return new ThingInStore(thingInStoreDTO.getId(),
                thingInStoreDTO.getName(),
                thingInStoreDTO.getDescription(),
                thingInStoreDTO.getPrix(),
                thingInStoreDTO.isStarted(),
                thing);
    }

    public ThingInStoreDTO toDTO(ThingInStore thingInStore) {
        Client c = thingInStore.getClient();
        ThingInStoreDTO thingInStoreDTO = new ThingInStoreDTO(
                thingInStore.getId(),
                thingInStore.getThing().getId(),
                thingInStore.getName(),
                thingInStore.getDescription(),
                thingInStore.getPrix(),
                thingInStore.isStarted());
        if (c != null) {
            ClientDTO clientDTO = clientDTOMapper.toDTO(thingInStore.getClient());
            thingInStoreDTO.setClient(clientDTO);
        }
        return thingInStoreDTO;
    }
}
