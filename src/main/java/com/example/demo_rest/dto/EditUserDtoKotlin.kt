package com.example.demo_rest.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class EditUserDtoKotlin(
    @field:NotNull(message = "Imię nie może być puste")
    @field:Size(min = 10, message = "Imię musi zawierać co najmniej 10 znaków")
    val name: String,

    @field:NotNull(message = "Numer telefonu nie może być pusty")
    @field:Size(min = 9, max = 12, message = "Numer telefonu musi zawierać od 9 do 12 znaków")
    val phoneNumber: String,

    @field:NotNull(message = "Hasło nie może być puste")
    @field:Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,20}$",
        message = "Hasło musi zawierać: 1 dużą literę, 1 małą, 1 liczbę, 1 znak specjalny oraz mieć od 8 do 16 znaków"
    )
    val password: String
)