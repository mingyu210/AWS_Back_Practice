package com.example.springBoot.controller;


import com.example.springBoot.entity.Image;
import com.example.springBoot.repository.ImageRepository;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import io.awspring.cloud.s3.S3Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.Instant;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final S3Operations s3Operations;
    private final ImageRepository imageRepository;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    public ImageController(S3Operations s3Operations, ImageRepository imageRepository) {
        this.s3Operations = s3Operations;
        this.imageRepository = imageRepository;
    }

    @PostMapping
    public ResponseEntity<Image> uploadImage(@RequestPart("image") MultipartFile image) {

        String fileName = Instant.now().getEpochSecond() + "_" + image.getOriginalFilename();

        try (InputStream inputStream = image.getInputStream()) {

            S3Resource s3Resource = s3Operations.upload(
                    bucketName,
                    fileName,
                    inputStream,
                    ObjectMetadata.builder()
                            .contentType(image.getContentType())
                            .build()
            );

            String imageUrl = s3Resource.getURL().toString();

            Image saved = imageRepository.save(new Image(imageUrl));

            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            throw new RuntimeException("이미지 업로드 실패", e);
        }
    }

}
