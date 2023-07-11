package com.glg204.wotstore.webofthing.controller;

import com.glg204.wotstore.webofthing.dto.ThingDTO;
import com.glg204.wotstore.webofthing.dto.ThingTypeDTO;
import com.glg204.wotstore.webofthing.service.ThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/thing")
public class ThingController {

    @Autowired
    private ThingService thingService;

    @GetMapping("")
    public ResponseEntity<List<ThingDTO>> getThings() {
        List<ThingDTO> thingDTOList = thingService.getThings();
        if (thingDTOList.isEmpty()) {
            return ResponseEntity.ok(thingDTOList); //or   return ResponseEntity.notFound().build(); (update frontend part)
        } else {
            return ResponseEntity.ok(thingDTOList);
        }
    }

    @GetMapping("/type")
    public ResponseEntity<List<ThingTypeDTO>> getThingsType() {
        List<ThingTypeDTO> thingDTOList = thingService.getThingsType();
        if (thingDTOList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(thingDTOList);
        }
    }

    @GetMapping("/{type}")
    public ResponseEntity<ThingDTO> getByType(@PathVariable String type) {
        return thingService.getByTitle(type).map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.notFound().build()
        );
    }
}
