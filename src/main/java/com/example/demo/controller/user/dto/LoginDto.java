package com.example.demo.controller.user.dto;

public class LoginDto {
    private String id;
    private String pw;

    public void setId(String id) {
        this.id = id;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }
}
