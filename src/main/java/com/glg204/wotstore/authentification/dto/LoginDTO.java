package com.glg204.wotstore.authentification.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

public class LoginDTO {

    @NotNull
    private String email;

    @NotNull
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
