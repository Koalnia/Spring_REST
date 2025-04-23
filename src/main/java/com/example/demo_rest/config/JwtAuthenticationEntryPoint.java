package com.example.demo_rest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        // Ustawienie statusu odpowiedzi HTTP na 401 (Unauthorized)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Ustawienie typu odpowiedzi na JSON
        response.setContentType("application/json;charset=UTF-8");

        // Przygotowanie błędu w formacie JSON
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("error", "Nieautoryzowany dostęp. Zaloguj się żeby otrzymać aktywny token JWT.");
        errorDetails.put("message", authException.getMessage());
        errorDetails.put("status", HttpServletResponse.SC_UNAUTHORIZED);

        // Zapisanie odpowiedzi JSON
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorDetails));
    }
}
