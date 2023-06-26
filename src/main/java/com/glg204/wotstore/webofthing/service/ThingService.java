package com.glg204.wotstore.webofthing.service;

import org.springframework.stereotype.Service;

@Service
public interface ThingService {
    void getByTitle(String title);
}
