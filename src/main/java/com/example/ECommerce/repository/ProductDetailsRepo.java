package com.example.ECommerce.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ECommerce.entity.ECommerce;

@Repository

public interface ProductDetailsRepo extends JpaRepository<ECommerce, Long> {
    List<ECommerce> findByPrice(Integer type);
    // List<RealNest> findByFilter(String location,Integer price,String type);
    ECommerce findByProductNameAndPrice(
        String ProductName,
        Integer price
    );
}


