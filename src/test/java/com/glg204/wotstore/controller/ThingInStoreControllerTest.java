package com.glg204.wotstore.controller;

import com.glg204.wotstore.webofthing.controller.ThingInStoreController;
import com.glg204.wotstore.webofthing.dto.ThingInStoreDTO;
import com.glg204.wotstore.webofthing.service.ThingInStoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ThingInStoreControllerTest {

    @Mock
    private ThingInStoreService thingInStoreService;

    @InjectMocks
    private ThingInStoreController thingInStoreController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetThingsInStore() {

        ThingInStoreDTO mockDTO1 = new ThingInStoreDTO(
                1L,                    // id
                "thingTypeId123",      // thingTypeId
                "Mock Thing",          // name
                "Mock Description",    // description
                99.99,                 // prix
                true                   // started
        );
        ThingInStoreDTO mockDTO2 = new ThingInStoreDTO(
                1L,                    // id
                "thingTypeId123",      // thingTypeId
                "Mock Thing",          // name
                "Mock Description",    // description
                99.99,                 // prix
                true                   // started
        );
        List<ThingInStoreDTO> mockDTOList = new ArrayList<>();
        mockDTOList.add(mockDTO1);
        mockDTOList.add(mockDTO2);
        when(thingInStoreService.getThingsInStore()).thenReturn(mockDTOList);

        ResponseEntity<List<ThingInStoreDTO>> response = thingInStoreController.getThingsInStore();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockDTOList, response.getBody());
        verify(thingInStoreService, times(1)).getThingsInStore();
    }

    @Test
    void testSaveThingInStore() {
        ThingInStoreDTO mockDTO = new ThingInStoreDTO(
                1L,               // id
                "thingTypeId123",      // thingTypeId
                "Mock Thing",          // name
                "Mock Description",    // description
                99.99,                 // prix
                true                   // started
        );

        Long mockId = 123L;
        when(thingInStoreService.save(any())).thenReturn(Optional.of(mockId));

        ResponseEntity<Long> response = thingInStoreController.saveThingInStore(mockDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockId, response.getBody());
        verify(thingInStoreService, times(1)).save(any());
    }
}
