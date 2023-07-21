package com.glg204.wotstore.webofthing.controller;

import com.glg204.wotstore.webofthing.dto.ThingTypeDTO;
import com.glg204.wotstore.webofthing.dto.ThingTypeSelectDTO;
import com.glg204.wotstore.webofthing.service.ThingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/thingtype")
public class ThingTypeController {

    @Autowired
    private ThingTypeService thingTypeService;

    @GetMapping("")
    public ResponseEntity<List<ThingTypeSelectDTO>> getThingsType() {
        List<ThingTypeSelectDTO> thingDTOList = thingTypeService.getThingsType();
        if (thingDTOList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(thingDTOList);
        }
    }

    @GetMapping("/{type}")
    public ResponseEntity<ThingTypeDTO> getByType(@PathVariable String type) {
        return thingTypeService.getByTitle(type).map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.notFound().build()
        );
    }
}
