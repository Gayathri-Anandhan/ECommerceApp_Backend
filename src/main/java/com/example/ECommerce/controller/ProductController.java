package com.example.ECommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import com.example.ECommerce.entity.ECommerce;
import com.example.ECommerce.service.ProductService;
import com.example.ECommerce.service.ImageService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("ECommerce/api/products")

public class ProductController {
    @Autowired
    private ProductService ProductService;
    @GetMapping("/allproducts")
    public List<ECommerce> getAllProducts() {
        return ProductService.getAllProducts();
    }

    @GetMapping("/viewproducts")
    public ECommerce viewProduct(@RequestParam("id") Long id) {
        return ProductService.getProductById(id);
    }

    @GetMapping("/filterproducts")
    public List<ECommerce> filterProduct(@RequestParam("price") Integer price,
            @RequestParam("ProductName") String ProductName) {
        return ProductService.getProductByFilters(price, ProductName);
    }

    @PutMapping(value = "/updateProducts", consumes = "multipart/form-data")
    public ECommerce updateProduct(
            @RequestParam("product") String productJson,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        ECommerce r = mapper.readValue(productJson, ECommerce.class);

        if (file != null && !file.isEmpty()) {
            String imageUrl = imageService.uploadImage(file);
            r.setImageUrl(imageUrl);
        }

        return ProductService.updateProduct(r.getId(), r);
    }

    @DeleteMapping("/deleteProducts")
    public String deleteProduct(@RequestParam("id") Long id) {
        ECommerce delprops = ProductService.deleteProduct(id);
        return "Product deleted successfully with id: " + id;
    }

    @Autowired
    private ImageService imageService;

    @PostMapping(value = "/saveProduct", consumes = "multipart/form-data")
    public ECommerce saveProduct(
            @RequestParam("Product") String productJson,
            @RequestParam("file") MultipartFile file) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        ECommerce r = mapper.readValue(productJson, ECommerce.class);

        String imageUrl = imageService.uploadImage(file);
        r.setImageUrl(imageUrl);

        return ProductService.savedetails(r);
    }
}

