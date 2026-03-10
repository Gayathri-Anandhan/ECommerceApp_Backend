package com.example.ECommerce.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.ECommerce.entity.ECommerce;
import com.example.ECommerce.repository.ProductDetailsRepo;
import com.example.ECommerce.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductDetailsRepo productDetailsRepo;

    @InjectMocks
    private ProductService productService;

    private ECommerce product;

    @BeforeEach
    void setUp() {
        product = new ECommerce();
        product.setId(1L);
        product.setProductName("Laptop");
        product.setDescription("Gaming Laptop");
        product.setPrice(50000);
        product.setImageUrl("image.jpg");
    }

    // Test save product
    @Test
    void testSaveDetails() {

        when(productDetailsRepo.save(product)).thenReturn(product);

        ECommerce result = productService.savedetails(product);

        assertNotNull(result);
        assertEquals("Laptop", result.getProductName());

        verify(productDetailsRepo).save(product);
    }

    // Test get all products
    @Test
    void testGetAllProducts() {

        List<ECommerce> products = Arrays.asList(product);

        when(productDetailsRepo.findAll()).thenReturn(products);

        List<ECommerce> result = productService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals("Laptop", result.get(0).getProductName());

        verify(productDetailsRepo).findAll();
    }

    // Test get product by id
    @Test
    void testGetProductById() {

        when(productDetailsRepo.findById(1L)).thenReturn(Optional.of(product));

        ECommerce result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(50000, result.getPrice());

        verify(productDetailsRepo).findById(1L);
    }

    // Test update product
    @Test
    void testUpdateProduct() {

        ECommerce updated = new ECommerce();
        updated.setProductName("Updated Laptop");
        updated.setDescription("New Description");
        updated.setPrice(60000);
        updated.setImageUrl("newimage.jpg");

        when(productDetailsRepo.findById(1L)).thenReturn(Optional.of(product));
        when(productDetailsRepo.save(any(ECommerce.class))).thenReturn(product);

        ECommerce result = productService.updateProduct(1L, updated);

        assertEquals("Updated Laptop", result.getProductName());

        verify(productDetailsRepo).save(any(ECommerce.class));
    }

    // Test delete product
    @Test
    void testDeleteProduct() {

        when(productDetailsRepo.findById(1L)).thenReturn(Optional.of(product));

        ECommerce result = productService.deleteProduct(1L);

        assertNotNull(result);
        assertEquals("Laptop", result.getProductName());

        verify(productDetailsRepo).deleteById(1L);
    }
}