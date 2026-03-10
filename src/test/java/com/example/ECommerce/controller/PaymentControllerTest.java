package com.example.ECommerce.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.example.ECommerce.entity.Payment;
import com.example.ECommerce.service.PaymentService;
import com.razorpay.RazorpayException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    // 1️⃣ Test createOrder
    @Test
    void testCreateOrder() throws RazorpayException {

        when(paymentService.createOrder(500))
                .thenReturn("order_12345");

        String result = paymentController.createOrder(500);

        assertEquals("order_12345", result);
    }

    // 2️⃣ Test getAllPayments
    @Test
    void testGetAllPayments() {

        Payment payment = new Payment();
        payment.setId("order_123");
        payment.setAmount(500);

        List<Payment> payments = List.of(payment);

        when(paymentService.getAllPayments()).thenReturn(payments);

        List<Payment> result = paymentController.getAllPayments();

        assertEquals(1, result.size());
        assertEquals(500, result.get(0).getAmount());
    }
}