package com.example.demo.domain.entity.passbookkinds.service.select;

import com.example.demo.domain.entity.passbookkinds.domain.PassBookKinds;
import com.example.demo.mapper.PassBookKindsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SelectPassBookKindsServiceImpl implements SelectPassBookKindsService {
    private final PassBookKindsMapper passBookKindsMapper;

    @Override
    public List<PassBookKinds> selectAll(String userId) {
        return passBookKindsMapper.selectAllByUserId(userId);
    }
}
