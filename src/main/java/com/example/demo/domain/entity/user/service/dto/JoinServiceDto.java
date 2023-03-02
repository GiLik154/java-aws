package com.example.demo.domain.entity.user.service.dto;

public class JoinServiceDto {

    public JoinServiceDto(String id, String pw, String name) {
        this.id = id;
        this.pw = pw;
        this.name = name;
    }

    private String id;
    private String pw;
    private String name;

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getName() {
        return name;
    }
}
