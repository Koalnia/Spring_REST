package com.example.demo_rest.service;

import com.example.demo_rest.dto.EditUserDto;
import com.example.demo_rest.dto.UserShowDto;
import com.example.demo_rest.entity.Role;
import com.example.demo_rest.entity.User;
import com.example.demo_rest.exception.UserException;
import com.example.demo_rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserShowDto> getAllUsers() {
       List<UserShowDto> userDtoList = userRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
       return userDtoList;
    }

    public UserShowDto convertToDto(User user) {
        return new UserShowDto(user);
    }

    public User checkUserAccess(Long id, boolean toDelete) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        if(!Objects.equals(currentUser.getId(), id) && currentUser.getRole()!= Role.ADMIN)
            throw new AccessDeniedException("Tylko Admin może edytować lub usuwać nie swoje konto");
        if(Objects.equals(currentUser.getId(), id) && toDelete)
            throw new AccessDeniedException("Nie możesz usunąć swojego konta, skontaktuj się z Adminem");
        return userRepository.findById(id).orElseThrow(() -> new UserException("Brak użytkownika o id:" + id));
    }
    public UserShowDto editUser(EditUserDto editUserDto, Long id) {
        User user = checkUserAccess(id, false);
        user.setName(editUserDto.getName());
        user.setPhoneNumber(editUserDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(editUserDto.getPassword()));
        userRepository.save(user);
        return new UserShowDto(user);
    }

    public UserShowDto deleteUser(Long id) {
        User user = checkUserAccess(id, true);
        userRepository.delete(user);
        return new UserShowDto(user);
    }

    public String getUserVerificationCode(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        if(currentUser.getRole()!= Role.ADMIN)
            throw new AccessDeniedException("Tylko Admin sprawdzić kod weryfikacyjny");
        if(Objects.equals(currentUser.getId(), id))
            throw new AccessDeniedException("Nie możesz dostać kodu weryfikacyjnego swojego konta, sprawdź swój adres email");

        User user = userRepository.findById(id).orElseThrow(() -> new UserException("Brak użytkownika o id:" + id));
        if(user.isEnabled())
            throw new UserException("Konto jest już zweryfikowane");
        else
            return user.getVerificationCode();
    }
}