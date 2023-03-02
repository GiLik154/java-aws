package com.example.demo.controller.user.dto;

import com.example.demo.domain.entity.user.service.dto.ReviseServiceDto;

public class ReviseDto {
    private String name;
    private int age;
    private String sex;
    private String phone;
    private String hobby;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public int getAge() {
        return age;
    }

    public ReviseServiceDto convertServiceDto(){
        return new ReviseServiceDto(this.name, this.age, this.sex, this.phone, this.hobby);
    }

}
