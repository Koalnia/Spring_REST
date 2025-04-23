package com.example.demo_rest.config;


import com.example.demo_rest.exception.AdvertisementException;
import com.example.demo_rest.exception.UserException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ Obsługa błędów walidacji JSON-a (np. @Valid w kontrolerze)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }


    // ✅ Obsługa braku uprawnień
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Brak uprawnień: " + ex.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Żądany zasób nie jest obsługiwany.");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handleNoResourceFoundException(NoResourceFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Żądany zasób nie istnieje.");
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>("Ta metoda HTTP nie jest obsługiwana: " + ex.getMethod(), HttpStatus.METHOD_NOT_ALLOWED);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>("Zły parametr: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        return new ResponseEntity<>("Wystąpiła wartość null: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrity(DataIntegrityViolationException ex) {
        return new ResponseEntity<>("Powtórzono wartość unikalną: " + ex.getMessage(), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleLogin(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Błędne dane logowania "+ ex.getMessage());
    }

    @ExceptionHandler(AdvertisementException.class)
    public ResponseEntity<String> handleAdvertisementNotFoundException(AdvertisementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Błąd związany z ogłoszeniem: "+ ex.getMessage());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleUser(UserException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Błąd związany z użytkownikiem: "+ ex.getMessage());
    }

    // ✅ Obsługa ogólnych wyjątków
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd serwera: " + ex.toString()+""+ ex.getMessage());
    }

}