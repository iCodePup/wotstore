package com.glg204.wotstore.authentification.controller;

import com.glg204.wotstore.authentification.domain.WOTUser;
import com.glg204.wotstore.authentification.dto.AuthDTO;
import com.glg204.wotstore.authentification.dto.LoginDTO;
import com.glg204.wotstore.authentification.dto.WOTUserDTO;
import com.glg204.wotstore.authentification.service.WOTUserService;
import com.glg204.wotstore.config.TokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController()
@RequestMapping("/auth/")
public class IdentificationController {

    @Autowired
    private WOTUserService wotUserService;

    @Autowired
    private AuthenticationManager authenticationManager;

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
