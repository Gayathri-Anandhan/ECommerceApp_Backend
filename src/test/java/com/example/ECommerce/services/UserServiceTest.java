package com.example.ECommerce.services;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.ECommerce.entity.Login;
import com.example.ECommerce.repository.LoginDetailsRepo;
import com.example.ECommerce.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private LoginDetailsRepo loginDetailsRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private Login login;

    @BeforeEach
    void setUp() {
        login = new Login();
        login.setId(1L);
        login.setUsername("testuser");
        login.setPassword("pass");
        login.setName("Test User");
        login.setEmail("test@example.com");
        login.setRole("ADMIN");
        login.setPhoneno("1234567890");
    }

    @Test
    void testCreateLogin() {
        when(loginDetailsRepo.save(login)).thenReturn(login);

        Login saved = userService.createLogin(login);

        assertNotNull(saved);
        assertEquals("testuser", saved.getUsername());
        verify(loginDetailsRepo).save(login);
    }

    @Test
    void testGetAllUsers() {
        when(loginDetailsRepo.findAll()).thenReturn(List.of(login));

        List<Login> users = userService.getAllUsers();

        assertEquals(1, users.size());
        assertEquals("testuser", users.get(0).getUsername());
    }

    @Test
    void testGetUserById_Found() {
        when(loginDetailsRepo.findById(1L)).thenReturn(Optional.of(login));

        Optional<Login> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }

    @Test
    void testGetUserByUsername_Found() {
        when(loginDetailsRepo.findByUsername("testuser")).thenReturn(Optional.of(login));

        Optional<Login> result = userService.getUserByUsername("testuser");

        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }

    @Test
    void testFindByUsername_ThrowsException() {
        when(loginDetailsRepo.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.findByUsername("unknown"));
    }

    @Test
    void testUpdateLogin() {
        Login updates = new Login();
        updates.setName("Updated Name");
        updates.setPassword("newpass");
        updates.setRole("USER");
        updates.setEmail("updated@example.com");
        updates.setPhoneno("9999999999");
        updates.setUsername("updateduser");

        when(loginDetailsRepo.findById(1L)).thenReturn(Optional.of(login));
        when(passwordEncoder.encode("newpass")).thenReturn("encodedpass");
        when(loginDetailsRepo.save(any(Login.class))).thenAnswer(i -> i.getArgument(0));

        Login updated = userService.updateLogin(1L, updates);

        assertEquals("Updated Name", updated.getName());
        assertEquals("encodedpass", updated.getPassword());
        assertEquals("USER", updated.getRole());
        verify(loginDetailsRepo).save(updated);
    }

    @Test
    void testUpdateLogin_NotFound() {
        when(loginDetailsRepo.findById(2L)).thenReturn(Optional.empty());

        Login updates = new Login();
        assertThrows(EntityNotFoundException.class, () -> userService.updateLogin(2L, updates));
    }

    @Test
    void testDeleteLogin() {
        when(loginDetailsRepo.existsById(1L)).thenReturn(true);

        userService.deleteLogin(1L);

        verify(loginDetailsRepo).deleteById(1L);
    }

    @Test
    void testDeleteLogin_NotFound() {
        when(loginDetailsRepo.existsById(2L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> userService.deleteLogin(2L));
    }

    @Test
    void testLoadUserByUsername() {
        when(loginDetailsRepo.findByUsername("testuser")).thenReturn(Optional.of(login));

        UserDetails userDetails = userService.loadUserByUsername("testuser");

        assertEquals("testuser", userDetails.getUsername());
        assertEquals("pass", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }
}