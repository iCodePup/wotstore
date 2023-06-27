package com.glg204.wotstore.webofthing.controller;

import com.glg204.wotstore.webofthing.dto.ThingDTO;
import com.glg204.wotstore.webofthing.service.ThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/thing")
public class ThingController {

    @Autowired
    private ThingService thingService;

    @GetMapping("")
    public ResponseEntity<List<ThingDTO>> getThings() {
        return thingService.getThings().map(things -> {
            return ResponseEntity.ok(things);
        }).orElseGet(() ->
                ResponseEntity.notFound().build()
        );
    }

    @GetMapping("/{type}")
    public ResponseEntity<ThingDTO> getByType(@PathVariable String type) {

        return thingService.getByTitle(type).map(thing -> ResponseEntity.ok(thing)).orElseGet(() ->
                ResponseEntity.notFound().build()
        );
    }

}
