package com.example.dreaminterpretationjourney;

public class DiaryEntry {
    private String content;

    public DiaryEntry() {} // Firebase는 기본 생성자 필요

    public DiaryEntry(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}