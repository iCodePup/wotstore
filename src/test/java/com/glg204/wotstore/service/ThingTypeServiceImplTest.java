package com.glg204.wotstore.service;

import com.glg204.wotstore.webofthing.dao.ThingTypeDAO;
import com.glg204.wotstore.webofthing.domain.ThingType;
import com.glg204.wotstore.webofthing.dto.ThingTypeDTO;
import com.glg204.wotstore.webofthing.dto.ThingTypeSelectDTO;
import com.glg204.wotstore.webofthing.service.ThingTypeDTOMapper;
import com.glg204.wotstore.webofthing.service.ThingTypeSelectDTOMapper;
import com.glg204.wotstore.webofthing.service.ThingTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ThingTypeServiceImplTest {

    @Mock
    private ThingTypeDAO thingTypeDAO;

    @Mock
    private ThingTypeDTOMapper thingTypeDTOMapper;

    @Mock
    private ThingTypeSelectDTOMapper thingTypeSelectDTOMapper;

    @InjectMocks
    private ThingTypeServiceImpl thingTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetByTitle() {
        String title = "SampleTitle";
        ThingType thingType = new ThingType("1", title, null, "SampleDescription");
        when(thingTypeDAO.getByTitle(title)).thenReturn(Optional.of(thingType));
        thingTypeService.getByTitle(title);
        verify(thingTypeDAO).getByTitle(title);
        verify(thingTypeDTOMapper).toDTO(thingType);
    }

    @Test
    void testGetThingsType() {
        List<ThingType> thingTypes = new ArrayList<>();
        ThingType thingType = new ThingType("1", "SampleTitle", null, "SampleDescription");
        thingTypes.add(thingType);
        when(thingTypeDAO.getThingTypes()).thenReturn(thingTypes);
        thingTypeService.getThingsType();
        verify(thingTypeDAO).getThingTypes();
        verify(thingTypeSelectDTOMapper).toDTO(thingType);
    }

    @Test
    void testGetThings() {
        List<ThingType> thingTypes = new ArrayList<>();
        ThingType thingType = new ThingType("1", "SampleTitle", null, "SampleDescription");
        thingTypes.add(thingType);
        when(thingTypeDAO.getThingTypes()).thenReturn(thingTypes);
        thingTypeService.getThings();
        verify(thingTypeDAO).getThingTypes();
        verify(thingTypeDTOMapper).toDTO(thingType);
    }
}
