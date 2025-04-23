package com.example.demo_rest.controller;

import com.example.demo_rest.dto.AdvertisementCreationDto;
import com.example.demo_rest.dto.AdvertisementShowDto;
import com.example.demo_rest.entity.Advertisement;
import com.example.demo_rest.exception.AdvertisementException;
import com.example.demo_rest.repository.AdvertisementRepository;
import com.example.demo_rest.service.AdvertisementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {
    @Autowired
    private final AdvertisementService advertisementService;
    @Autowired
    private final AdvertisementRepository advertisementRepository;


    @GetMapping()
    public ResponseEntity<List<AdvertisementShowDto>> allAdvertisements() {
        List<AdvertisementShowDto> userDtoList = advertisementService.getAllAdvertisements();
        return ResponseEntity.ok(userDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementShowDto> oneAdvertisement(@PathVariable Long id) {
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow(() -> new AdvertisementException("Brak advertisementu o id:" + id));
        return ResponseEntity.ok(new AdvertisementShowDto(advertisement));
    }

    @GetMapping("/title")
    public ResponseEntity<List<AdvertisementShowDto>> oneAdvertisementByTitle(@RequestParam String title) {
        return ResponseEntity.ok(advertisementService.getAdvertisementsByTitle(title));
    }

    @GetMapping("/email")
    public ResponseEntity<List<AdvertisementShowDto>> oneAdvertisementByUserEmail(@RequestParam String email) {
        return ResponseEntity.ok(advertisementService.getAdvertisementsByUserEmail(email));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AdvertisementShowDto>> getAdvertisementsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(advertisementService.getAdvertisementsByUserId(userId));
    }

    @PostMapping()
    public ResponseEntity<AdvertisementShowDto> createAdvertisement(@Valid @RequestBody AdvertisementCreationDto advertisementCreationDto){
        return ResponseEntity.ok(advertisementService.createAdvertisement(advertisementCreationDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdvertisementShowDto> editAdvertisement(@Valid @RequestBody AdvertisementCreationDto advertisementCreationDto, @PathVariable Long id){
        return ResponseEntity.ok(advertisementService.editAdvertisement(advertisementCreationDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>  deleteAdvertisement(@PathVariable Long id) {
        return ResponseEntity.ok("Usunięto Advertisement o tytule: "+ advertisementService.deleteAdvertisement(id));
    }

}