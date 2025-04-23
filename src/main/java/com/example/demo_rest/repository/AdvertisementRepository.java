package com.example.demo_rest.repository;

import com.example.demo_rest.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {


    List<Advertisement> findByTitleContainingIgnoreCase(String title);

    List<Advertisement> findByUser_Email(String email);

    List<Advertisement> findByUser_Id(Long userId);
}
