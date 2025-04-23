package com.example.demo_rest.controller;

import com.example.demo_rest.dto.EditUserDto;
import com.example.demo_rest.dto.UserShowDto;
import com.example.demo_rest.entity.User;
import com.example.demo_rest.exception.UserException;
import com.example.demo_rest.repository.UserRepository;
import com.example.demo_rest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final UserRepository userRepository;


    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @GetMapping("/me")
    public ResponseEntity<String> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(authentication.toString() + "  " + currentUser.toString());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> hiAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(authentication.toString() + "  " + currentUser.toString());
    }

    @GetMapping()
    public ResponseEntity<List<UserShowDto>> allUsers() {
        List<UserShowDto> userDtoList = userService.getAllUsers();
        return ResponseEntity.ok(userDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserShowDto> oneUser( @PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("Brak użytkownika o id:" + id));
        return ResponseEntity.ok(new UserShowDto(user));
    }

    @GetMapping("/code/{id}")
    public ResponseEntity<String> getUserVerificationCode( @PathVariable Long id) {
        String kod = userService.getUserVerificationCode(id);
        return ResponseEntity.ok("Kod weryfikacyjny użytkownika to: " + kod);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<UserShowDto> editUser(@Valid @RequestBody EditUserDto editUserDto, @PathVariable Long id) {
        UserShowDto userShowDto = userService.editUser(editUserDto, id);
        return ResponseEntity.ok(userShowDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        UserShowDto userShowDto = userService.deleteUser(id);
        return ResponseEntity.ok("Usunięto użytkownika:" + userShowDto.getName());
    }

}
