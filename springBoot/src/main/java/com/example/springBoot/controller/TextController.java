package com.example.springBoot.controller;

import com.example.springBoot.dto.TextRequest;
import com.example.springBoot.dto.TextResponse;
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
    @PostMapping(value = "/in", consumes = "application/json")
    public TextEntity saveText(@RequestBody TextRequest dto) {
        return textRepository.save(new TextEntity(dto.getContent()));
    }

    // 전체 조회
    @GetMapping("/out")
    public List<TextResponse> getTexts() {
        return textRepository.findAll()
                .stream()
                .map(text -> new TextResponse(text.getId(), text.getContent()))
                .toList();
    }
}
