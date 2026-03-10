package com.example.ECommerce.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.example.ECommerce.entity.Payment;
import com.example.ECommerce.repository.PaymentRepo;
import com.example.ECommerce.service.PaymentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepo paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Payment payment;

    @BeforeEach
    void setUp() {

        payment = new Payment();
        payment.setId("order_123");
        payment.setAmount(500);
        payment.setCurrency("INR");
        payment.setStatus("CREATED");
    }

    // Test getAllPayments()
    @Test
    void testGetAllPayments() {

        List<Payment> payments = Arrays.asList(payment);

        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(500, result.get(0).getAmount());

        verify(paymentRepository).findAll();
    }

    // Test repository save call
    @Test
    void testSavePayment() {

        paymentRepository.save(payment);

        verify(paymentRepository, times(1)).save(payment);
    }
}