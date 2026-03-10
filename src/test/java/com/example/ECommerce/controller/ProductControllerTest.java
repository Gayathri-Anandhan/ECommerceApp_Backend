package com.example.ECommerce.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.example.ECommerce.entity.ECommerce;
import com.example.ECommerce.service.ProductService;
import com.example.ECommerce.service.ImageService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private ProductController productController;

    private ECommerce product;

    @BeforeEach
    void setUp() {
        product = new ECommerce();
        product.setId(1L);
        product.setProductName("Phone");
        product.setPrice(10000);
        product.setImageUrl("image.jpg");
    }

    // 1️⃣ Get all products
    @Test
    void testGetAllProducts() {

        List<ECommerce> products = List.of(product);

        when(productService.getAllProducts()).thenReturn(products);

        List<ECommerce> result = productController.getAllProducts();

        assertEquals(1, result.size());
        assertEquals("Phone", result.get(0).getProductName());
    }

    // 2️⃣ View product by ID
    @Test
    void testViewProduct() {

        when(productService.getProductById(1L)).thenReturn(product);

        ECommerce result = productController.viewProduct(1L);

        assertEquals("Phone", result.getProductName());
        assertEquals(10000, result.getPrice());
    }

    // 3️⃣ Filter products
    @Test
    void testFilterProduct() {

        List<ECommerce> products = List.of(product);

        when(productService.getProductByFilters(10000, "Phone"))
                .thenReturn(products);

        List<ECommerce> result =
                productController.filterProduct(10000, "Phone");

        assertEquals(1, result.size());
        assertEquals("Phone", result.get(0).getProductName());
    }

    // 4️⃣ Update product
    @Test
    void testUpdateProduct() throws Exception {

        String productJson =
                "{\"id\":1,\"productName\":\"Phone\",\"price\":10000}";

        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(true);

        when(productService.updateProduct(eq(1L), any(ECommerce.class)))
                .thenReturn(product);

        ECommerce result = productController.updateProduct(productJson, file);

        assertEquals("Phone", result.getProductName());
    }

    // 5️⃣ Delete product
    @Test
    void testDeleteProduct() {

        when(productService.deleteProduct(1L)).thenReturn(product);

        String result = productController.deleteProduct(1L);

        assertTrue(result.contains("Product deleted successfully"));
    }

    // 6️⃣ Save product
    @Test
    void testSaveProduct() throws Exception {

        String productJson =
                "{\"productName\":\"Phone\",\"price\":10000}";

        MultipartFile file = mock(MultipartFile.class);

        when(imageService.uploadImage(file)).thenReturn("image.jpg");

        when(productService.savedetails(any(ECommerce.class)))
                .thenReturn(product);

        ECommerce result = productController.saveProduct(productJson, file);

        assertEquals("Phone", result.getProductName());
    }
}