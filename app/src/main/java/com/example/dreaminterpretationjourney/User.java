package com.example.dreaminterpretationjourney;

public class User {
    private String userId;
    private String email;

    public User() {
        // Firestore용 빈 생성자
    }

    public User(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }
    public String getEmail() {
        return email;
    }
}