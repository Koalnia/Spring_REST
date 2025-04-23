package com.example.demo_rest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "advertisements")
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private String price;

    @Column(name = "duration")
    private String duration;

    @Column(name = "created_at")
    private String createdAt;

    public Advertisement() {
    }


    public Advertisement(Long id, User user, String title, String description, String price, String duration, String createdAt) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createdAt = createdAt;
    }

    public Advertisement(Long id, String title, String description, String price, String duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }

    public Advertisement(User user, String title, String description, String price, String duration, String createdAt) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createdAt = createdAt;
    }
}