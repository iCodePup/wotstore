package com.glg204.wotstore.authentification.controller;

import com.glg204.wotstore.authentification.domain.WOTUser;
import com.glg204.wotstore.authentification.dto.WOTUserDTO;
import com.glg204.wotstore.authentification.service.WOTUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController()
@RequestMapping("/auth/")
public class IdentificationController {

    @Autowired
    private WOTUserService wotUserService;

    @GetMapping("/me")
    public ResponseEntity<WOTUserDTO> identification(Principal p) {
        Optional<WOTUser> wotUserOptional = wotUserService.getUserByUsername(p.getName());
        return wotUserOptional.map(wotUser ->
                ResponseEntity.ok(new WOTUserDTO(wotUser.getEmail(), wotUser.getFirstName(), wotUser.getLastName(), wotUser.getRole().name()))
        ).orElseGet(() -> ResponseEntity.ok(null));
    }

}
