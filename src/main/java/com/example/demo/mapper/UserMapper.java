package com.example.demo.mapper;

import com.example.demo.domain.entity.user.domain.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {
    int add(User user);
    int getId(User user);
    int reviseInfo(User user);
    int selectUser(String userId);
    String getPw(String id);
    User getInfo(String userId);

}
