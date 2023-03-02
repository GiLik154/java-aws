package com.example.demo.domain.entity.passbookkinds.service.delete;

import com.example.demo.domain.entity.passbookkinds.domain.PassBookKinds;
import com.example.demo.mapper.PassBookKindsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeletePassBookKindsServiceImpl implements DeletePassBookKindsService {
    private final PassBookKindsMapper passBookKindsMapper;

    @Override
    public boolean delete(String userId, int pbKindsNum) {
        if (isCheckCreator(userId, pbKindsNum)) {
            return passBookKindsMapper.deleteByPbKindsNum(pbKindsNum) == 1;
        }
        return false;
    }

    private boolean isCheckCreator(String userId, int pbKindsNum) {
        Optional<PassBookKinds> nullCheck = Optional.ofNullable(passBookKindsMapper.selectAllByPbKindsNum(pbKindsNum));

        if (nullCheck.isPresent()) {
            String[] userArr = nullCheck.get().getUser().split("-");
            return userArr[0].equals(userId);
        }

        return false;
    }
}
