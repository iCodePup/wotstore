package com.glg204.wotstore.client.controller;

import com.glg204.wotstore.client.dto.ClientDTO;
import com.glg204.wotstore.client.service.ClientService;
import com.glg204.wotstore.webofthing.dto.ThingInStoreDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
            return ResponseEntity.internalServerError().body("Une erreur s'est produite lors de votre achat");
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
            return ResponseEntity.badRequest().body("Objet connecté déja démarré");
        }
    }

    @PostMapping("/thinginstore/stop")
    public ResponseEntity<String> stopThingInStore(@Valid @RequestBody ThingInStoreDTO thingInStoreDTO) {
        if (clientService.stopThingInStore(thingInStoreDTO.getId())) {
            return ResponseEntity.ok("Objet connecté arreté");
        } else {
            return ResponseEntity.badRequest().body("Impossible d'arreter cet objet connecté");
        }
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationExceptions(Exception ex) {
        StringBuilder builder = new StringBuilder();

        if (ex instanceof MethodArgumentNotValidException validationException) {
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
