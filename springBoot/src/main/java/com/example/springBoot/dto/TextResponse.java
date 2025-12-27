package com.example.springBoot.dto;

public class TextResponse {
    private Long id;
    private String content;

    public TextResponse(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
