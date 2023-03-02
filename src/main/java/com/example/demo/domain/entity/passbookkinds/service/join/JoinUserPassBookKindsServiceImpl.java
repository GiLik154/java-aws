package com.example.demo.domain.entity.passbookkinds.service.join;

import com.example.demo.mapper.PassBookKindsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinUserPassBookKindsServiceImpl implements JoinUserPassBookKindsService {
    private final PassBookKindsMapper passBookKindsMapper;

    @Override
    public boolean join(String userId, String token) {
        if (isCompareToken(token) && isDuplicatedUser(userId, token)) {
            return passBookKindsMapper.updateUserByToken(isCombineUser(userId, token), token) == 1;
        }
        return false;
    }

    private String isCombineUser(String userId, String token) {
        return passBookKindsMapper.selectAllByToken(token).getUser() + "-" + userId;
    }

    private boolean isCompareToken(String token) {
        return !Objects.isNull(passBookKindsMapper.selectAllByToken(token));
    }

    private boolean isDuplicatedUser(String userId, String token) {
        String[] userArr = passBookKindsMapper.selectAllByToken(token).getName().split("-");

        for (String user : userArr) {
            if (user.equals(userId)) {
                return false;
            }
        }
        return true;
    }
}
