package com.glg204.wotstore.dao;


import com.glg204.wotstore.webofthing.dao.ThingTypeDAO;
import com.glg204.wotstore.webofthing.dao.ThingTypeDAOImpl;
import com.glg204.wotstore.webofthing.domain.ThingType;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ThingInStoreDAOImplTest {

    private final Connection connection = mock(Connection.class);

    private final DataSource dataSource = mock(DataSource.class);

    private final Statement statement = mock(Statement.class);

    private final PreparedStatement preparedStatement = mock(PreparedStatement.class);

    private final ResultSet resultSet = mock(ResultSet.class);

    private final CallableStatement callableStatement = mock(CallableStatement.class);

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);


    @BeforeEach
    public void setup() throws Exception {
        given(this.dataSource.getConnection()).willReturn(this.connection);
        given(this.connection.prepareStatement(anyString())).willReturn(this.preparedStatement);
        given(this.preparedStatement.executeQuery()).willReturn(this.resultSet);
        given(this.preparedStatement.executeQuery(anyString())).willReturn(this.resultSet);
        given(this.preparedStatement.getConnection()).willReturn(this.connection);
        given(this.statement.getConnection()).willReturn(this.connection);
        given(this.statement.executeQuery(anyString())).willReturn(this.resultSet);
        given(this.connection.prepareCall(anyString())).willReturn(this.callableStatement);
        given(this.callableStatement.getResultSet()).willReturn(this.resultSet);


    }


    @Test
    public void testGetThingTypeById_ExistingId_ReturnsThingType() throws Exception {
        ThingTypeDAO thingTypeDAO = new ThingTypeDAOImpl();
        ReflectionTestUtils.setField(thingTypeDAO, "jdbcTemplate", jdbcTemplate);
        ThingType thingType = new ThingType("0", "test", new JSONArray(), "");
        final String sql = "select * from thing_type where id = ?";
        given(resultSet.next()).willReturn(true, false);
        given(resultSet.getString("typeAsJson")).willReturn("[OnOffSwitch]");
        given(resultSet.getString("title")).willReturn("test");
        given(connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)
        ).willReturn(preparedStatement);
        Optional<ThingType> resultOpt = thingTypeDAO.getById(0L);
        if (resultOpt.isPresent()) {
            ThingType result = resultOpt.get();
            Assertions.assertEquals(thingType.getId(), result.getId());
            Assertions.assertEquals(thingType.getTitle(), result.getTitle());

        } else {
            Assertions.fail();
        }
    }

    @Test
    public void testGetByTitle_ExistingTitle_ReturnsThingType() throws Exception {
        ThingTypeDAO thingTypeDAO = new ThingTypeDAOImpl();
        ReflectionTestUtils.setField(thingTypeDAO, "jdbcTemplate", jdbcTemplate);
        ThingType expectedThingType = new ThingType("0", "test", new JSONArray(), "");
        final String sql = "select * from thing_type where title = ?";
        given(resultSet.next()).willReturn(true, false);
        given(resultSet.getString("id")).willReturn("0");
        given(resultSet.getString("typeAsJson")).willReturn("[OnOffSwitch]");
        given(resultSet.getString("title")).willReturn("test");
        given(resultSet.getString("description")).willReturn("");
        given(connection.prepareStatement(sql)).willReturn(preparedStatement);

        Optional<ThingType> resultOpt = thingTypeDAO.getByTitle("test");
        if (resultOpt.isPresent()) {
            ThingType result = resultOpt.get();
            Assertions.assertEquals(expectedThingType.getId(), result.getId());
            Assertions.assertEquals(expectedThingType.getTitle(), result.getTitle());
        } else {
            Assertions.fail();
        }
    }

    @Test
    public void testGetThingTypes_ReturnsListOfThingTypes() throws Exception {
        ThingTypeDAO thingTypeDAO = new ThingTypeDAOImpl();
        ReflectionTestUtils.setField(thingTypeDAO, "jdbcTemplate", jdbcTemplate);

        List<ThingType> expectedThingTypes = new ArrayList<>();
        expectedThingTypes.add(new ThingType("0", "test1", new JSONArray(), ""));
        expectedThingTypes.add(new ThingType("1", "test2", new JSONArray(), ""));

        final String sql = "select * from thing_type";

        given(this.jdbcTemplate.queryForList(anyString())).willReturn(Collections.emptyList());

        given(resultSet.next()).willReturn(true, true, false);
        given(resultSet.getString("id")).willReturn("0", "1");
        given(resultSet.getString("typeAsJson")).willReturn("[OnOffSwitch]", "[TemperatureSensor]");
        given(resultSet.getString("title")).willReturn("test1", "test2");
        given(resultSet.getString("description")).willReturn("", "");
        given(connection.prepareStatement(sql)).willReturn(preparedStatement);

        List<ThingType> resultThingTypes = thingTypeDAO.getThingTypes();
        assertEquals(expectedThingTypes.size(), resultThingTypes.size());

        for (int i = 0; i < expectedThingTypes.size(); i++) {
            ThingType expected = expectedThingTypes.get(i);
            ThingType result = resultThingTypes.get(i);

            assertEquals(expected.getId(), result.getId());
            assertEquals(expected.getTitle(), result.getTitle());
            // Add additional assertions for other fields if needed
        }
    }

}








