package com.example.springBoot.repository;

import com.example.springBoot.entity.TextEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextRepository extends JpaRepository<TextEntity, Long> {
}
