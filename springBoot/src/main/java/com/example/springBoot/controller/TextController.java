package com.example.springBoot.controller;

import com.example.springBoot.entity.TextEntity;
import com.example.springBoot.repository.TextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/texts")
public class TextController {
    private final TextRepository textRepository;

    public TextController(TextRepository textRepository) {
        this.textRepository = textRepository;
    }

    // 입력 저장
    @PostMapping("/in")
    public TextEntity saveText(@RequestBody String content) {
        return textRepository.save(new TextEntity(content));
    }

    // 전체 조회
    @GetMapping("/out")
    public List<TextEntity> getTexts() {
        return textRepository.findAll();
    }
}
