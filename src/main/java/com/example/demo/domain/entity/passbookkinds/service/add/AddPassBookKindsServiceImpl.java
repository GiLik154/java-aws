package com.example.demo.domain.entity.passbookkinds.service.add;

import com.example.demo.mapper.PassBookKindsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class AddPassBookKindsServiceImpl implements AddPassBookKindsService {
    private final PassBookKindsMapper passBookKindsMapper;

    @Override
    public boolean add(String name, String userId) {
        return passBookKindsMapper.add(name, userId, creatToken()) == 1;
    }

    private String creatToken() {
        while (true) {
            StringBuilder stringBuilder = new StringBuilder().append(creatKey());

            for (int i = 0; i < 2; i++) {
                stringBuilder.append("-").append(creatKey());
            }

            if (isDuplicatedToken(stringBuilder.toString())) {
                return stringBuilder.toString();
            }
        }
    }

    private String creatKey() {
        int key = 0;

        try {
            key = SecureRandom.getInstanceStrong().nextInt(10000);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return String.valueOf(key);
    }

    private boolean isDuplicatedToken(String token) {
        return Objects.isNull(passBookKindsMapper.selectAllByToken(token));
    }

}
