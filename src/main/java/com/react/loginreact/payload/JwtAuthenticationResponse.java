package com.react.loginreact.payload;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {

    private String accesToken;
    private String typeToken= "Bearer";

    public JwtAuthenticationResponse(String accesToken) {
        this.accesToken = accesToken;
    }
}
