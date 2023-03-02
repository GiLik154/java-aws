package com.example.demo.domain.entity.user.domain;

import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;


@Getter
public class User {
    private String id;
    private String pw;
    private String name;
    private int age;
    private String sex;
    private String phone;
    private String hobby;

    public User(){
        super();
    }

    public User(String id, String pw, String name) {
        this.id = id;
        this.pw = pw;
        this.name = name;
    }

    public User(String id, String name, int age, String sex, String phone, String hobby) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.phone = phone;
        this.hobby = hobby;
    }

    public void encodingPw(PasswordEncoder passwordEncoder) {
        this.pw = passwordEncoder.encode(this.pw);
    }
}
