package com.glg204.wotstore.client.controller;

import com.glg204.wotstore.authentification.dto.WOTUserDTO;
import com.glg204.wotstore.authentification.exception.EmailAlreadyExistsException;
import com.glg204.wotstore.authentification.dto.AuthDTO;
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

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController()
@RequestMapping("/client/create")
public class RegistrationController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping()
    public ResponseEntity<AuthDTO> createAccount(@Valid @RequestBody ClientDTO clientDTO) {
        try {
            try {
                clientService.save(passwordEncoder, clientDTO);
                String token = authenticateAndGetToken(clientDTO.getEmail(), clientDTO.getPassword());
                WOTUserDTO registeredClientDTO =
                        new WOTUserDTO(
                                clientDTO.getEmail(),
                                clientDTO.getFirstName(),
                                clientDTO.getLastName(),
                                "CLIENT");
                AuthDTO registeredDTO = new AuthDTO(registeredClientDTO, token);
                return ResponseEntity.ok(registeredDTO);
            } catch (EmailAlreadyExistsException e) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(null);
            }
        } catch (Exception e) {
            e.printStackTrace(); //todo to be removed debug purpose
        }
        return ResponseEntity.ok(null);
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

}
