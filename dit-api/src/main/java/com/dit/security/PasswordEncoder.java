package com.dit.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordEncoder implements org.springframework.security.crypto.password.PasswordEncoder {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10, new SecureRandom());

    @Override
    public String encode(CharSequence rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}