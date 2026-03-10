package com.example.ECommerce.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.example.ECommerce.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @InjectMocks
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        // set fields via reflection because they are private and @Value
        org.springframework.test.util.ReflectionTestUtils.setField(imageService, "useUnsigned", true);
        org.springframework.test.util.ReflectionTestUtils.setField(imageService, "unsignedPreset", "preset123");
        org.springframework.test.util.ReflectionTestUtils.setField(imageService, "folder", "testFolder");
    }

    @Test
    void testUploadImageUnsigned() throws Exception {

        // Mock MultipartFile
        MultipartFile file = new MockMultipartFile(
                "file",
                "test.jpg",
                "image/jpeg",
                "dummy image content".getBytes()
        );

        // Mock Cloudinary uploader
        when(cloudinary.uploader()).thenReturn(uploader);

        // Mock upload result
        Map<String, Object> mockResult = new HashMap<>();
        mockResult.put("secure_url", "https://cloudinary.com/test.jpg");

        when(uploader.upload(any(byte[].class), anyMap())).thenReturn(mockResult);

        // Call service method
        String url = imageService.uploadImage(file);

        // Verify
        assertEquals("https://cloudinary.com/test.jpg", url);

        verify(uploader).upload(any(byte[].class), anyMap());
    }

    @Test
    void testUploadImageSigned() throws Exception {

        // Set useUnsigned to false
        org.springframework.test.util.ReflectionTestUtils.setField(imageService, "useUnsigned", false);

        MultipartFile file = new MockMultipartFile(
                "file",
                "test2.jpg",
                "image/jpeg",
                "dummy content".getBytes()
        );

        when(cloudinary.uploader()).thenReturn(uploader);

        Map<String, Object> mockResult = new HashMap<>();
        mockResult.put("secure_url", "https://cloudinary.com/test2.jpg");

        when(uploader.upload(any(byte[].class), anyMap())).thenReturn(mockResult);

        String url = imageService.uploadImage(file);

        assertEquals("https://cloudinary.com/test2.jpg", url);

        verify(uploader).upload(any(byte[].class), anyMap());
    }
}