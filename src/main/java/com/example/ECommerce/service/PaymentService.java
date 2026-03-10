package com.example.ECommerce.service;
import java.util.List;
import com.example.ECommerce.entity.Payment;
import com.example.ECommerce.repository.PaymentRepo;
import com.razorpay.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    private final PaymentRepo paymentRepository;

    public PaymentService(PaymentRepo paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public String createOrder(int amount) throws RazorpayException {

        RazorpayClient razorpay = new RazorpayClient(keyId, keySecret);

        JSONObject options = new JSONObject();
        options.put("amount", amount * 100); // amount in paise
        options.put("currency", "INR");
        options.put("receipt", "order_rcptid_11");

        Order order = razorpay.orders.create(options);

        Payment payment = new Payment(
                order.get("id"),
                amount,
                "INR",
                "CREATED");

        // Use injected repository instance
        paymentRepository.save(payment);

        return order.toString();
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}