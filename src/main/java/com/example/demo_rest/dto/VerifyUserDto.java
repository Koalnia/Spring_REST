package com.example.demo_rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class VerifyUserDto {
    private String email;
    private String verificationCode;
}