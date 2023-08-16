package com.glg204.wotstore.dao;

import com.glg204.wotstore.webofthing.dao.ThingInStoreDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ThingInStoreDAOImplTest {

    @Autowired
    private ThingInStoreDAO thingInStoreDAO;

    @Test
    public void testGetThingInStoreById_ExistingId_ReturnsThingInStore() {

    }
}
