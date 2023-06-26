package com.glg204.wotstore.authentification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthDTO {

    private String jwt;

    @JsonProperty("user")
    private WOTUserDTO wotUserDTO;

    public AuthDTO(WOTUserDTO wotUserDTO, String token) {
        this.wotUserDTO = wotUserDTO;
        this.jwt = token;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public WOTUserDTO getWotUserDTO() {
        return wotUserDTO;
    }

    public void setWotUserDTO(WOTUserDTO wotUserDTO) {
        this.wotUserDTO = wotUserDTO;
    }
}
