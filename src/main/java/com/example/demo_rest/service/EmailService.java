package com.example.demo_rest.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Async
    public void sendVerificationEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);

        emailSender.send(message);
    }
}