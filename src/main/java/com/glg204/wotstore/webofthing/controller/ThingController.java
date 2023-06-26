package com.glg204.wotstore.webofthing.controller;

import com.glg204.wotstore.webofthing.service.ThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/thing")
public class ThingController {

    @Autowired
    private ThingService thingService;

    @GetMapping("/{type}")
    public ResponseEntity<String> getByType(@PathVariable String type) {
        try {

            thingService.getByTitle(type);

            return ResponseEntity.ok("");

        } catch (Exception e) {
            e.printStackTrace(); //todo to be removed debug purpose
        }
        return ResponseEntity.ok(null);
    }

}
