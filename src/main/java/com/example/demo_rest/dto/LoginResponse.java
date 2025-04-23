package com.example.demo_rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private String token;
    private String expiresIn;

    public LoginResponse(String token, String expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }
}