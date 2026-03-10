package com.example.ECommerce.service;
import com.cloudinary.utils.ObjectUtils;
import com.example.ECommerce.entity.ECommerce;
import com.example.ECommerce.repository.ProductDetailsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

import com.cloudinary.Cloudinary;

import java.util.List;


@Service
// the service annotation tells the framework that it is business logic
public class ProductService {
    @Autowired
    private ProductDetailsRepo productDetailsRepo;
    public ECommerce savedetails(ECommerce e) {
        return productDetailsRepo.save(e);
    }

    public List<ECommerce> getAllProducts() {
        return productDetailsRepo.findAll();
    }

    public ECommerce getProductById(Long id) {
        return productDetailsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<ECommerce> getProductByFilters(Integer price,String ProductName) {
        return productDetailsRepo.findByPrice(price);
    }

    public ECommerce updateProduct(Long id, ECommerce updateProps) {
        ECommerce exisProduct = productDetailsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with this id: " + id + "is not found"));
        exisProduct.setProductName(updateProps.getProductName());
        exisProduct.setDescription(updateProps.getDescription());
        exisProduct.setPrice(updateProps.getPrice());
        exisProduct.setImageUrl(updateProps.getImageUrl());
        return productDetailsRepo.save(exisProduct);
    }

    public ECommerce deleteProduct(Long id) {
        ECommerce exisProduct = productDetailsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productDetailsRepo.deleteById(id);
        return exisProduct;
    }

    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) throws Exception {

        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.emptyMap()
        );

        return uploadResult.get("secure_url").toString();
    }
    
}