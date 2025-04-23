package com.example.demo_rest.service;

import com.example.demo_rest.dto.AdvertisementCreationDto;
import com.example.demo_rest.dto.AdvertisementShowDto;
import com.example.demo_rest.entity.Advertisement;
import com.example.demo_rest.entity.Role;
import com.example.demo_rest.entity.User;
import com.example.demo_rest.exception.AdvertisementException;
import com.example.demo_rest.repository.AdvertisementRepository;
import com.example.demo_rest.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class AdvertisementService {

    @Autowired
    private final AdvertisementRepository advertisementRepository;
    @Autowired
    private final UserRepository userRepository;

    public AdvertisementService(AdvertisementRepository advertisementRepository, UserRepository userRepository) {
        this.advertisementRepository = advertisementRepository;
        this.userRepository = userRepository;
    }

    public List<AdvertisementShowDto> getAllAdvertisements() {
        return advertisementRepository.findAll().stream().
                sorted(Comparator.comparing(Advertisement::getTitle))
                .map(AdvertisementShowDto::new)
                .toList();

    }
    public List<AdvertisementShowDto> getAdvertisementsByTitle(String title) {
        return advertisementRepository.findByTitleContainingIgnoreCase(title).stream()
                .sorted(Comparator.comparing(Advertisement::getTitle))
                .map(AdvertisementShowDto::new)
                .toList();
    }
    public List<AdvertisementShowDto> getAdvertisementsByUserEmail(String email) {
        return advertisementRepository.findByUser_Email(email).stream()
                .sorted(Comparator.comparing(Advertisement::getTitle))
                .map(AdvertisementShowDto::new)
                .toList();
    }

    public List<AdvertisementShowDto> getAdvertisementsByUserId(Long userId) {
        return advertisementRepository.findByUser_Id(userId).stream()
                .sorted(Comparator.comparing(Advertisement::getTitle))
                .map(AdvertisementShowDto::new)
                .toList();
    }

    public AdvertisementShowDto createAdvertisement(@Valid AdvertisementCreationDto advertisementCreationDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String date = LocalDateTime.now().format(formatter);
        Advertisement advertisement = advertisementRepository.save(new Advertisement(currentUser,
                advertisementCreationDto.getTitle(),
                advertisementCreationDto.getDescription(),
                advertisementCreationDto.getPrice(),
                advertisementCreationDto.getDuration(),
                date));
        return new AdvertisementShowDto(advertisement);
    }

    public AdvertisementShowDto editAdvertisement(@Valid AdvertisementCreationDto advertisementCreationDto, Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow(() -> new AdvertisementException("Brak ogłoszenia o id:" + id));

        if(!Objects.equals(advertisement.getUser().getEmail(), currentUser.getEmail()) && currentUser.getRole()!= Role.ADMIN)
            throw new AccessDeniedException("Tylko administrator może usuwać ogłoszenia innych użytkowników");

        advertisement.setTitle(advertisementCreationDto.getTitle());
        advertisement.setDescription(advertisementCreationDto.getDescription());
        advertisement.setPrice(advertisementCreationDto.getPrice());
        advertisement.setDuration(advertisementCreationDto.getDuration());
        advertisementRepository.save(advertisement);
        return new AdvertisementShowDto(advertisement);

    }

    public String deleteAdvertisement(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow(() -> new AdvertisementException("Brak ogłoszenia o id:" + id));

        if(!Objects.equals(advertisement.getUser().getEmail(), currentUser.getEmail()) && currentUser.getRole()!= Role.ADMIN)
            throw new AccessDeniedException("Tylko administrator może usuwać ogłoszenia innych użytkowników");
        advertisementRepository.delete(advertisement);
        return advertisement.getTitle();
    }


}
