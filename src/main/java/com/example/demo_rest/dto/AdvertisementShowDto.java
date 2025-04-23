package com.example.demo_rest.dto;

import com.example.demo_rest.entity.Advertisement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AdvertisementShowDto {


    private String title;
    private String description;
    private String price;
    private String duration;
    private String email;
    private String phoneNumber;
    private String createdAt;

    public AdvertisementShowDto(Advertisement advertisement)
    {
        this.title = advertisement.getTitle();
        this.description = advertisement.getDescription();
        this.price = advertisement.getPrice();
        this.duration = advertisement.getDuration();
        this.email = advertisement.getUser().getEmail();
        this.phoneNumber = advertisement.getUser().getPhoneNumber();
        this.createdAt = advertisement.getCreatedAt();
    }
}
