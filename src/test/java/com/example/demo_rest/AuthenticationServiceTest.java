package com.example.demo_rest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo_rest.dto.VerifyUserDto;
import com.example.demo_rest.entity.User;
import com.example.demo_rest.exception.UserException;
import com.example.demo_rest.repository.UserRepository;
import com.example.demo_rest.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    // testy dla public void verifyUser(VerifyUserDto input)
    @Test
    public void testVerifyUser_UserAlreadyVerified() {
        // Arrange
        VerifyUserDto input = new VerifyUserDto();
        input.setEmail("user@example.com");
        input.setVerificationCode("123456");

        User user = new User("user@example.com", "password");
        user.setEnabled(true); // Konto już zweryfikowane
        when(userRepository.findByEmail(input.getEmail())).thenReturn(Optional.of(user));

        // Act & Assert
        UserException exception = assertThrows(UserException.class, () -> {
            authenticationService.verifyUser(input);
        });
        assertEquals("Użytkownik został już zweryfikowany", exception.getMessage());
    }

    @Test
    public void testVerifyUser_VerificationCodeExpired() {
        // Arrange
        VerifyUserDto input = new VerifyUserDto();
        input.setEmail("user@example.com");
        input.setVerificationCode("123456");

        User user = new User("user@example.com", "password");
        user.setVerificationCode("123456");
        user.setEnabled(false);
        user.setVerificationCodeExpiresAt(LocalDateTime.now().minusMinutes(1)); // Kod wygasł
        when(userRepository.findByEmail(input.getEmail())).thenReturn(Optional.of(user));

        // Act & Assert
        UserException exception = assertThrows(UserException.class, () -> {
            authenticationService.verifyUser(input);
        });
        assertEquals("Kod weryfikacyjny stracił swoją ważność", exception.getMessage());
    }

    @Test
    public void testVerifyUser_Success() {
        // Arrange
        VerifyUserDto input = new VerifyUserDto();
        input.setEmail("user@example.com");
        input.setVerificationCode("123456");

        User user = new User("user@example.com", "password");
        user.setVerificationCode("123456");
        user.setEnabled(false);
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(5)); // Kod ważny
        when(userRepository.findByEmail(input.getEmail())).thenReturn(Optional.of(user));
        var time = LocalDateTime.now();
        // Act
        authenticationService.verifyUser(input);

        // Assert
        verify(userRepository, times(1)).save(user);
        assertTrue(user.isEnabled());
        assertEquals("",user.getVerificationCode());
        assertEquals(time,user.getVerificationCodeExpiresAt());
    }

    @Test
    public void testVerifyUser_InvalidVerificationCode() {
        // Arrange
        VerifyUserDto input = new VerifyUserDto();
        input.setEmail("user@example.com");
        input.setVerificationCode("123456");

        User user = new User("user@example.com", "password");
        user.setVerificationCode("654321"); // Zły kod
        user.setEnabled(false);
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(5)); // Kod ważny
        when(userRepository.findByEmail(input.getEmail())).thenReturn(Optional.of(user));

        // Act & Assert
        UserException exception = assertThrows(UserException.class, () -> {
            authenticationService.verifyUser(input);
        });
        assertEquals("Niepoprawny kod weryfikacyjny", exception.getMessage());
    }

    @Test
    public void testVerifyUser_UserNotFound() {
        // Arrange
        VerifyUserDto input = new VerifyUserDto();
        input.setEmail("user@example.com");
        input.setVerificationCode("123456");

        when(userRepository.findByEmail(input.getEmail())).thenReturn(Optional.empty()); // Użytkownik nie istnieje

        // Act & Assert
        UserException exception = assertThrows(UserException.class, () -> {
            authenticationService.verifyUser(input);
        });
        assertEquals("Brak takiego użytkownika", exception.getMessage());
    }
}
