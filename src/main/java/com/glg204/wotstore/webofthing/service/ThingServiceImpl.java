package com.glg204.wotstore.webofthing.service;

import com.glg204.wotstore.webofthing.dao.ThingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThingServiceImpl implements ThingService {

    @Autowired
    ThingDAO thingDAO;

    @Override
    public void getByTitle(String title) {
        thingDAO.getByTitle(title);

    }
}
