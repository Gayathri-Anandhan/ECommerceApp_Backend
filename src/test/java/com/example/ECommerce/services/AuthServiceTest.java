package com.example.ECommerce.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.example.ECommerce.config.JwtUtil;
import com.example.ECommerce.entity.Login;
import com.example.ECommerce.repository.LoginDetailsRepo;
import com.example.ECommerce.service.AuthService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private LoginDetailsRepo loginDetailsRepo;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private Login login;

    @BeforeEach
    void setUp() {
        login = new Login();
        login.setUsername("testuser");
        login.setPassword("password");
        login.setEmail("test@mail.com");
        login.setRole("USER");
    }

    // 1️⃣ SignUp success
    @Test
    void testSignUpSuccess() {

        when(loginDetailsRepo.findByUsername("testuser"))
                .thenReturn(Optional.empty());

        when(loginDetailsRepo.findByEmail("test@mail.com"))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("password"))
                .thenReturn("encodedPassword");

        String result = authService.signUp(login);

        assertEquals("User registered successfully", result);
        verify(loginDetailsRepo).save(login);
    }

    // 2️⃣ Username already exists
    @Test
    void testSignUpUsernameExists() {

        when(loginDetailsRepo.findByUsername("testuser"))
                .thenReturn(Optional.of(login));

        String result = authService.signUp(login);

        assertEquals("Username already exists", result);
    }

    // 3️⃣ Email already exists
    @Test
    void testSignUpEmailExists() {

        when(loginDetailsRepo.findByUsername("testuser"))
                .thenReturn(Optional.empty());

        when(loginDetailsRepo.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(login));

        String result = authService.signUp(login);

        assertEquals("Email already exists", result);
    }

    // 4️⃣ SignIn success
    @Test
    void testSignInSuccess() {

        Authentication authentication = mock(Authentication.class);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(authentication.getName()).thenReturn("testuser");

        when(jwtUtil.generateToken("testuser"))
                .thenReturn("mockToken");

        String token = authService.signIn("testuser", "password");

        assertEquals("mockToken", token);
    }
}