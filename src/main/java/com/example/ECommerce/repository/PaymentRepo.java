package com.example.ECommerce.repository;

import com.example.ECommerce.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Long> {

    Payment findById(String id);

}