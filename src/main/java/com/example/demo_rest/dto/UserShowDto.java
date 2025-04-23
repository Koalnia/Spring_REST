package com.example.demo_rest.dto;

import com.example.demo_rest.entity.User;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserShowDto {

    private String name;
    private String phoneNumber;
    private String email;
    private String role;

    public UserShowDto(String name, String phoneNumber, String email, String role) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
    }

    public UserShowDto(User user)
    {
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.role = user.getRole().name();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
