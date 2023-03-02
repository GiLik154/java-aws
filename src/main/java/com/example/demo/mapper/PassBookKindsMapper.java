package com.example.demo.mapper;

import com.example.demo.domain.entity.passbookkinds.domain.PassBookKinds;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface PassBookKindsMapper {
    int add(String name, String userId, String token);
    List<PassBookKinds> selectAllByUserId(String userId);
    PassBookKinds selectAllByPbKindsNum(int pbKindsNum);
    PassBookKinds selectAllByToken(String token);
    PassBookKinds selectByPbKindsNumAndUserId(String userId, int pbKindsNum);
    int updateUserByToken(String userArr, String token);
    int updateUserByPbKindsNum(String userArr, int pbKindsNum);
    int deleteByPbKindsNum(int pbKindsNum);
}