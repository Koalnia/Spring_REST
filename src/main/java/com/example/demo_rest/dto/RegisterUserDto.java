package com.example.demo_rest.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RegisterUserDto {
    @NotNull(message = "Imię nie może być puste")
    @Size(min = 10,message = "Imię musi zawierać co najmniej 10 znaków")
    private String name;

    @NotNull(message = "Numer telefonu nie może być pusty")
    @Size(min = 9,max = 12, message = "Numer telefonu musi zawierać od 9 do 12 znaków")
    private String phoneNumber;

    @NotNull(message = "E-mail nie może być pusty")
    @Size(min = 8,max = 50, message = "E-mail musi zawierać od 8 do 50 znaków")
    @Email
    private String email;

    @NotNull(message = "Hasło nie może być puste")
    @Pattern(regexp ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,20}$",
            message = "Hasło musi zawierać: 1 dużą literę, 1 małą, 1 liczbę, 1 znak specjalny oraz mieć od 8 do 16 znaków")
    private String password;
}