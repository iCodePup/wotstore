package com.glg204.wotstore.service;

import com.glg204.wotstore.client.domain.Client;
import com.glg204.wotstore.client.dto.ClientDTO;
import com.glg204.wotstore.domain.ThingMock;
import com.glg204.wotstore.webofthing.service.ThingInStoreService;
import com.glg204.wotstore.webofthing.dto.ThingInStoreDTO;
import com.glg204.wotstore.webofthing.dao.ThingInStoreDAO;
import com.glg204.wotstore.webofthing.domain.ThingInStore;
import com.glg204.wotstore.webofthing.service.ThingInStoreDTOMapper;
import com.glg204.wotstore.webofthing.dao.ThingTypeDAO;
import com.glg204.wotstore.webofthing.domain.ThingType;
import com.glg204.wotstore.webofthing.service.ThingInStoreServiceImpl;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ThingInStoreServiceTest {

    @Mock
    private ThingInStoreDAO thingInStoreDAO;

    @Mock
    private ThingInStoreDTOMapper thingInStoreDTOMapper;

    @InjectMocks
    private ThingInStoreServiceImpl thingInStoreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetThingsInStore() {

        ThingType mockThingType = new ThingType(
                "mockType",         // id
                "Mock Type",        // title
                new JSONArray(),    // type
                "Mock Description"  // description
        );
        Client mockClient = new Client();

        ThingMock mockThing = new ThingMock(
                "mockId",           // id
                "Mock Thing",       // title
                new JSONArray(),    // type
                "Mock Description"  // description
        );

        ThingInStore mockThing1 = new ThingInStore(
                1L,                            // id
                "Mock ThingInStore",           // name
                "Mock Description",            // description
                99.99,                         // prix
                true,                          // started
                mockThingType,
                mockThing
        );

        List<Optional<ThingInStore>> mockThingList = new ArrayList<>();
        mockThingList.add(Optional.of(mockThing1));

        List<ThingInStoreDTO> mockDTOList = mockThingList.stream()
                .map(thingInStoreOptional -> {
                    if (thingInStoreOptional.isPresent()) {
                        ThingInStore thingInStore = thingInStoreOptional.get();

                        ThingInStoreDTO dto = new ThingInStoreDTO(
                                thingInStore.getId(),
                                thingInStore.getThingType().getId(),
                                thingInStore.getName(),
                                thingInStore.getDescription(),
                                thingInStore.getPrix(),
                                thingInStore.isStarted()
                        );

                        if (thingInStore.getClient() != null) {
                            ClientDTO clientDTO = new ClientDTO("email", "", "", "", "");
                            dto.setClient(clientDTO);
                        }

                        return dto;
                    } else {
                        return null;
                    }
                })
                .collect(Collectors.toList());

        when(thingInStoreDAO.getThingsInStore()).thenReturn(mockThingList);
        when(thingInStoreDTOMapper.toDTO(any())).thenReturn(mockDTOList.get(0)); // Adjust this based on your mapper logic

        List<ThingInStoreDTO> result = thingInStoreService.getThingsInStore();

        assertEquals(mockDTOList.size(), result.size());
        assertEquals(mockDTOList, result);
        verify(thingInStoreDAO, times(1)).getThingsInStore();
    }

    @Test
    void testDeleteThingInStore() {
        Long mockId = 1L;
        when(thingInStoreDAO.delete(mockId)).thenReturn(true);

        Boolean result = thingInStoreService.delete(mockId);

        assertTrue(result);
        verify(thingInStoreDAO, times(1)).delete(mockId);
    }
}
