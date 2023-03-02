package com.example.demo.domain.entity.passbookkinds.service.cancel;

import com.example.demo.mapper.PassBookKindsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
@RequiredArgsConstructor
@Transactional
public class CancellingPassBookKindsServiceImpl implements CancellingPassBookKindsService {
    private final PassBookKindsMapper passBookKindsMapper;

    @Override
    public boolean cancelling(String userId, int pbKindsNum) {
        if (containsUser(userId, pbKindsNum)) {
            return false;
        }
        return passBookKindsMapper.updateUserByPbKindsNum(deleteUser(userId, pbKindsNum), pbKindsNum) == 1;
    }

    private String deleteUser(String userId, int pbKindsNum) {
        String[] userArr = passBookKindsMapper.selectAllByPbKindsNum(pbKindsNum).getUser().split("-");
        StringBuilder stringBuilder = new StringBuilder();

        for (String user : userArr) {
            if (!user.equals(userId)) {
                stringBuilder.append(user).append("-");
            }
        }
        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
    }

    private boolean containsUser(String userId, int pbKindsNum) {
        return Objects.isNull(passBookKindsMapper.selectByPbKindsNumAndUserId(userId, pbKindsNum));
    }
}
