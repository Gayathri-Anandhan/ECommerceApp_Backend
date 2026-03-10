package com.example.ECommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.GenerationType;

    @Entity
    @Table(name = "productdetails")
    @Data
    public class ECommerce {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String ProductName;
        private String description;
        private double price;
        private String imageUrl;

    }
