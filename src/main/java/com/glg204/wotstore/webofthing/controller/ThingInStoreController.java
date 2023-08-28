package com.glg204.wotstore.webofthing.controller;

import com.glg204.wotstore.webofthing.dto.ThingInStoreDTO;
import com.glg204.wotstore.webofthing.service.ThingInStoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/thinginstore")
public class ThingInStoreController {

    @Autowired
    private ThingInStoreService thingInStoreService;

    @GetMapping("")
    public ResponseEntity<List<ThingInStoreDTO>> getThingsInStore() {
        List<ThingInStoreDTO> thingInStoreDTOList = thingInStoreService.getThingsInStore();
        return ResponseEntity.ok(thingInStoreDTOList);
    }

    @GetMapping("/available")
    public ResponseEntity<List<ThingInStoreDTO>> getAvailableThingsInStore() {
        List<ThingInStoreDTO> thingInStoreDTOList = thingInStoreService.getThingsInStore();
        thingInStoreDTOList = thingInStoreDTOList.stream().filter(thingInStoreDTO -> thingInStoreDTO.getClient() == null).toList();
        return ResponseEntity.ok(thingInStoreDTOList);
    }

    @PostMapping()
    public ResponseEntity<Long> saveThingInStore(@Valid @RequestBody ThingInStoreDTO thingInStoreDTO) {
        return thingInStoreService.save(thingInStoreDTO).map(id -> ResponseEntity.ok(id)).orElseGet(() ->
                ResponseEntity.notFound().build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteThingInStore(@PathVariable Long id) {
        if (thingInStoreService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationExceptions(Exception ex) {
        StringBuilder builder = new StringBuilder();

        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validationException = (MethodArgumentNotValidException) ex;
            validationException.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                builder.append(String.format("%s: %s ", fieldName, errorMessage));
            });
        }

        String responseMessage = builder.toString().trim();
        String jsonResponse = String.format("{\"message\": \"%s\"}", responseMessage);
        return ResponseEntity.badRequest().body(jsonResponse);
    }
}
