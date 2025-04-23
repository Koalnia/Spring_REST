package com.example.demo_rest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AdvertisementCreationDto {


    @NotNull(message = "Tytuł ogłoszenia nie może być pusty")
    @Size(min = 8, max = 50, message = "Tytuł ogłoszenia musi zawierać od 8 do 50 znaków")
    private String title;

    @NotNull(message = "Opis ogłoszenia nie może być pusty")
    @Size(min = 9, max = 100, message = "Opis ogłoszenia musi zawierać od 9 do 50 znaków")
    private String description;

    @NotNull(message = "Cena ogłoszenia nie może być pusty")
    @Size(min = 1, max = 50, message = "Cena ogłoszenia musi zawierać od 1 do 50 znaków")
    private String price;

    @NotNull(message = "Czas trwania ogłoszenia nie może być pusty")
    @Size(min = 4, max = 50, message = "Czas trwania ogłoszenia musi zawierać od 4 do 50 znaków")
    private String duration;




}