package com.glg204.wotstore.client.controller;

import com.glg204.wotstore.authentification.dto.AuthDTO;
import com.glg204.wotstore.authentification.dto.WOTUserDTO;
import com.glg204.wotstore.authentification.exception.EmailAlreadyExistsException;
import com.glg204.wotstore.client.dto.ClientDTO;
import com.glg204.wotstore.client.service.ClientService;
import com.glg204.wotstore.config.TokenProvider;
import com.glg204.wotstore.webofthing.dto.ThingDTO;
import com.glg204.wotstore.webofthing.dto.ThingInStoreDTO;
import io.webthings.webthing.Thing;
import io.webthings.webthing.WebThingServer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping()
    public ResponseEntity<List<ClientDTO>> listClient() {
        List<ClientDTO> clientDTOList = clientService.getClients();
        return ResponseEntity.ok(clientDTOList);
    }

    @PostMapping("/thinginstore")
    public ResponseEntity<String> purchaseThingInStore(@Valid @RequestBody ThingInStoreDTO thingInStoreDTO, Principal p) {
        if (clientService.purchaseThingInStore(p, thingInStoreDTO.getId())) {
            return ResponseEntity.ok("Objet connecté acheté");
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/thinginstore")
    public ResponseEntity<List<ThingInStoreDTO>> getClientThingsInStore(Principal p) {
        List<ThingInStoreDTO> thingInStoreDTOList = clientService.getClientThingsInStore(p);
        return ResponseEntity.ok(thingInStoreDTOList);
    }

    @PostMapping("/thinginstore/start")
    public ResponseEntity<String> startThingInStore(@Valid @RequestBody ThingInStoreDTO thingInStoreDTO) {
        if (clientService.startThingInStore(thingInStoreDTO.getId())) {
            return ResponseEntity.ok("Objet connecté démarré");
        } else {
            return ResponseEntity.ok("Objet connecté déja démarré");
        }
    }

    @PostMapping("/thinginstore/stop")
    public ResponseEntity<String> stopThingInStore(@Valid @RequestBody ThingInStoreDTO thingInStoreDTO) {
        if (clientService.stopThingInStore(thingInStoreDTO.getId())) {
            return ResponseEntity.ok("Objet connecté arreté");
        } else {
            return ResponseEntity.ok("Impossible d'arreter cet objet connecté");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        //todo add verification double password
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
