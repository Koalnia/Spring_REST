package com.example.demo_rest.service;

import com.example.demo_rest.dto.LoginUserDto;
import com.example.demo_rest.dto.RegisterUserDto;
import com.example.demo_rest.dto.VerifyUserDto;
import com.example.demo_rest.entity.Role;
import com.example.demo_rest.entity.User;
import com.example.demo_rest.exception.UserException;
import com.example.demo_rest.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            EmailService emailService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public User signup(RegisterUserDto input) {
        User user = new User(input.getName(), input.getPhoneNumber(), input.getEmail(), passwordEncoder.encode(input.getPassword()));
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        user.setEnabled(false);
        user.setRole(Role.USER);
        sendVerificationEmail(user);
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UserException("Brak użytkownika o takim adresie email"));

        if (!user.isEnabled()) {
            throw new UserException("Konto nie zostało jeszcze zweryfikowane.Potwierdź swój adres email");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        input.getPassword()
                )
        );

        return user;
    }

    public void verifyUser(VerifyUserDto input) {
        Optional<User> optionalUser = userRepository.findByEmail(input.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(user.isEnabled())
                throw new UserException("Użytkownik został już zweryfikowany");
            if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
                throw new UserException("Kod weryfikacyjny stracił swoją ważność");
            }
            if (user.getVerificationCode().equals(input.getVerificationCode())) {
                user.setEnabled(true);
                user.setVerificationCode("");
                user.setVerificationCodeExpiresAt(LocalDateTime.now());
                userRepository.save(user);
            } else {
                throw new UserException("Niepoprawny kod weryfikacyjny");
            }
        } else {
            throw new UserException("Brak takiego użytkownika");
        }
    }

    public void resendVerificationCode(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isEnabled()) {
                throw new UserException("Użytkownik został już zweryfikowany");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(1));
            sendVerificationEmail(user);
            userRepository.save(user);
        } else {
            throw new UserException("Brak takiego użytkownika");
        }
    }

    private void sendVerificationEmail(User user) {
        String subject = "Weryfikacja konta";
        String verificationCode = "Kod weryfikacyjny " + user.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Witam w aplikacji testowej Spring Rest Api!</h2>"
                + "<p style=\"font-size: 16px;\">Użyj tego kodu do weryfikacji</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}