package com.glg204.wotstore.controller;


import com.glg204.wotstore.webofthing.controller.ThingTypeController;
import com.glg204.wotstore.webofthing.dto.ThingTypeDTO;
import com.glg204.wotstore.webofthing.dto.ThingTypeSelectDTO;
import com.glg204.wotstore.webofthing.service.ThingTypeService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ThingTypeControllerTest {

    @Mock
    private ThingTypeService thingTypeService;

    @InjectMocks
    private ThingTypeController thingTypeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetThingsType() {
        // Arrange
        List<ThingTypeSelectDTO> thingDTOList = new ArrayList<>();
        thingDTOList.add(new ThingTypeSelectDTO("type1", "Type 1", ""));
        thingDTOList.add(new ThingTypeSelectDTO("type2", "Type 2", ""));
        when(thingTypeService.getThingsType()).thenReturn(thingDTOList);
        ResponseEntity<List<ThingTypeSelectDTO>> response = thingTypeController.getThingsType();
        assertEquals(ResponseEntity.ok(thingDTOList), response);
    }

    @Test
    public void testGetByType() {
        ThingTypeDTO thingTypeDTO = new ThingTypeDTO("type1", "Type 1", null, "", null);
        when(thingTypeService.getByTitle("type1")).thenReturn(Optional.of(thingTypeDTO));
        ResponseEntity<ThingTypeDTO> response = thingTypeController.getByType("type1");
        assertEquals(ResponseEntity.ok(thingTypeDTO), response);
    }

    @Test
    public void testGetByTypeNotFound() {
        when(thingTypeService.getByTitle("type1")).thenReturn(Optional.empty());
        ResponseEntity<ThingTypeDTO> response = thingTypeController.getByType("type1");
        assertEquals(ResponseEntity.notFound().build(), response);
    }
}
