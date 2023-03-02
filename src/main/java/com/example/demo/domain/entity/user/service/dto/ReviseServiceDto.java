package com.example.demo.domain.entity.user.service.dto;

public class ReviseServiceDto {
    private String name;
    private int age;
    private String sex;
    private String phone;
    private String hobby;

    public ReviseServiceDto(String name, int age, String sex, String phone, String hobby) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.phone = phone;
        this.hobby = hobby;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getPhone() {
        return phone;
    }

    public String getHobby() {
        return hobby;
    }
}
