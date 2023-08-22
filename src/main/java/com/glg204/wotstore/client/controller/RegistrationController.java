package com.glg204.wotstore.client.controller;

import com.glg204.wotstore.authentification.dto.AuthDTO;
import com.glg204.wotstore.authentification.dto.WOTUserDTO;
import com.glg204.wotstore.authentification.exception.EmailAlreadyExistsException;
import com.glg204.wotstore.authentification.exception.PasswordMismatchException;
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
    public ResponseEntity<AuthDTO> createAccount(@Valid @RequestBody ClientDTO clientDTO) throws EmailAlreadyExistsException {

        // Check if password and repassword fields are equal
        if (!clientDTO.getPassword().equals(clientDTO.getConfirmPassword())) {
            throw new PasswordMismatchException("Password et repassword ne correspondent pas.");
        }
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
            throw new EmailAlreadyExistsException("Adresse email déja utilisée");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, PasswordMismatchException.class, EmailAlreadyExistsException.class})
    public ResponseEntity<Object> handleValidationExceptions(Exception ex) {
        StringBuilder builder = new StringBuilder();

        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validationException = (MethodArgumentNotValidException) ex;
            validationException.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                builder.append(String.format("%s: %s ", fieldName, errorMessage));
            });
        } else if (ex != null) {
            builder.append(ex.getMessage());
        }

        String responseMessage = builder.toString().trim();
        String jsonResponse = String.format("{\"message\": \"%s\"}", responseMessage);
        return ResponseEntity.badRequest().body(jsonResponse);
    }

    private String authenticateAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }
}
