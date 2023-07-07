package com.glg204.wotstore.authentification.controller;

import com.glg204.wotstore.authentification.domain.WOTUser;
import com.glg204.wotstore.authentification.dto.AuthDTO;
import com.glg204.wotstore.authentification.dto.LoginDTO;
import com.glg204.wotstore.authentification.dto.WOTUserDTO;
import com.glg204.wotstore.authentification.exception.EmailAlreadyExistsException;
import com.glg204.wotstore.authentification.service.WOTUserService;
import com.glg204.wotstore.client.dto.ClientDTO;
import com.glg204.wotstore.client.service.ClientService;
import com.glg204.wotstore.config.TokenProvider;
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

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController()
@RequestMapping("/auth/")
public class IdentificationController {

    @Autowired
    private WOTUserService wotUserService;


    @Autowired
    private ClientService clientService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@Valid @RequestBody LoginDTO loginDTO) {

        String token = authenticateAndGetToken(loginDTO.getEmail(), loginDTO.getPassword());
        Optional<WOTUser> wotUserOptional = wotUserService.getUserByUsername(loginDTO.getEmail());
        return wotUserOptional.map(wotUser -> {
                    WOTUserDTO wotUserDTO = new WOTUserDTO(wotUser.getEmail(), wotUser.getFirstName(), wotUser.getLastName(), wotUser.getRole().name());
                    AuthDTO registeredDTO = new AuthDTO(wotUserDTO, token);
                    return ResponseEntity.ok(registeredDTO);
                }
        ).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
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

    private String authenticateAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }


    @GetMapping("/me")
    public ResponseEntity<WOTUserDTO> identification(Principal p) {
        Optional<WOTUser> wotUserOptional = wotUserService.getUserByUsername(p.getName());
        return wotUserOptional.map(wotUser ->
                ResponseEntity.ok(new WOTUserDTO(wotUser.getEmail(), wotUser.getFirstName(), wotUser.getLastName(), wotUser.getRole().name()))
        ).orElseGet(() -> ResponseEntity.ok(null));
    }

}
