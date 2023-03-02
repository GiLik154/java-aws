package com.example.demo.controller.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
public class JoinDto {
    @Length(min = 5, max = 12)
    private String id;
    @Length(min = 5, max = 12)
    private String pw;
    private String name;
}


