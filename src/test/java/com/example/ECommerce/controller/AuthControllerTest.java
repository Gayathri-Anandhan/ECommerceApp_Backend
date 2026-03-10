package com.example.ECommerce.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.http.HttpStatus;
import java.util.*;
import com.example.ECommerce.entity.Login;
import com.example.ECommerce.service.AuthService;
import com.example.ECommerce.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    private Login login;

    @BeforeEach
    void setUp() {
        login = new Login();
        login.setUsername("testuser");
        login.setPassword("password");
        login.setRole("ROLE_USER");
        login.setName("Test User");
        login.setEmail("test@mail.com");
    }

    //  Sign-in success
    @Test
    void testSignInSuccess() {

        when(authService.signIn("testuser", "password"))
                .thenReturn("mockToken");

        when(userService.getUserByUsername("testuser"))
                .thenReturn(Optional.of(login));

        ResponseEntity<?> response = authController.signIn(login);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Map<?, ?> body = (Map<?, ?>) response.getBody();

        assertEquals("mockToken", body.get("token"));
        assertEquals("ROLE_USER", body.get("role"));
        assertEquals("Test User", body.get("name"));
    }

    // Sign-in user not found
    @Test
    void testSignInUserNotFound() {

        when(authService.signIn("testuser", "password"))
                .thenThrow(new UsernameNotFoundException("User not found"));

        ResponseEntity<?> response = authController.signIn(login);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    //  Sign-up success
    @Test
    void testSignUpSuccess() {

        when(authService.signUp(login)).thenReturn("User registered successfully");

        ResponseEntity<?> response = authController.signUp(login);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Map<?, ?> body = (Map<?, ?>) response.getBody();

        assertEquals("User registered successfully", body.get("message"));
    }

    //  Get all users
    @Test
    void testGetAllUsers() {

        List<Login> users = List.of(login);

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<?> response = authController.getAllLogins();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    //  Update user
    @Test
    void testUpdateUser() {

        when(userService.updateLogin(1L, login)).thenReturn(login);

        Login result = authController.updateProperty(1L, login);

        assertEquals("testuser", result.getUsername());
    }

    //  Delete user success
    @Test
    void testDeleteUserSuccess() {

        doNothing().when(userService).deleteLogin(1L);

        ResponseEntity<String> response = authController.deleteUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    //  Delete user not found
    @Test
    void testDeleteUserNotFound() {

        doThrow(new EntityNotFoundException("User not found"))
                .when(userService).deleteLogin(1L);

        ResponseEntity<String> response = authController.deleteUser(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}