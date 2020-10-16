package com.prageeth.dto;

public class AuthenticateResponseDTO {

    private String jwtToken;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public AuthenticateResponseDTO(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public AuthenticateResponseDTO() {
    }

}
