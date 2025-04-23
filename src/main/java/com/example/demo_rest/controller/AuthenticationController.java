package com.example.demo_rest.controller;

import com.example.demo_rest.dto.*;
import com.example.demo_rest.entity.User;
import com.example.demo_rest.service.AuthenticationService;
import com.example.demo_rest.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserShowDto> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        UserShowDto userShowDto = new UserShowDto(registeredUser);
        return ResponseEntity.ok(userShowDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto){
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, "1h");
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {
            authenticationService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("Użytkownik został poprawnie zweryfikowany");

    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Ponownie wysłano kod weryfikacyjny");
    }
}
