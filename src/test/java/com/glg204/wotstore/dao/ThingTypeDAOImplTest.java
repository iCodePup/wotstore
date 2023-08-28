package com.glg204.wotstore.dao;

import com.glg204.wotstore.webofthing.dao.ThingTypeDAOImpl;
import com.glg204.wotstore.webofthing.domain.ThingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class ThingTypeDAOImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ThingTypeDAOImpl thingTypeDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetThingTypes() {
        when(jdbcTemplate.queryForList(anyString())).thenReturn(List.of(
                mockDataRow("1", "Title 1", "[]", "Description 1"),
                mockDataRow("2", "Title 2", "[]", "Description 2")
        ));

        List<ThingType> result = thingTypeDAO.getThingTypes();
        assertEquals(2, result.size());
    }

    @Test
    void testGetByTitle() {
        String title = "SampleTitle";
        ThingType mockThingType = new ThingType("1", title, null, "Description");
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(mockThingType);
        Optional<ThingType> result = thingTypeDAO.getByTitle(title);
        assertEquals(title, result.orElse(new ThingType("", "", null, "")).getTitle());
    }

    @Test
    void testGetById() {
        Long thingTypeId = 1L;
        ThingType mockThingType = new ThingType("1", "Title", null, "Description");
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(mockThingType);
        Optional<ThingType> result = thingTypeDAO.getById(thingTypeId);
        assertEquals(String.valueOf(thingTypeId), result.orElse(new ThingType("", "", null, "")).getId());
    }


    // Helper method to create a mock data row
    private Map<String, Object> mockDataRow(String id, String title, String typeAsJson, String description) {
        Map<String, Object> row = new HashMap<>();
        row.put("id", id);
        row.put("title", title);
        row.put("typeAsJson", typeAsJson);
        row.put("description", description);
        return row;
    }
}