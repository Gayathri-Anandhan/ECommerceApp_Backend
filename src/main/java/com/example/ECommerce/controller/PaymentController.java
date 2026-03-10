package com.example.ECommerce.controller;
import java.util.List;

import com.example.ECommerce.entity.Payment;
import com.example.ECommerce.service.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ECommerce/payment")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-order")
    public String createOrder(@RequestParam int amount) throws RazorpayException {
        return paymentService.createOrder(amount);
    }

    @GetMapping("/all")
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }
}
