package com.example.demo.domain.entity.passbookkinds.service.select;

import com.example.demo.domain.entity.passbookkinds.domain.PassBookKinds;

import java.util.List;

public interface SelectPassBookKindsService {
    List<PassBookKinds> selectAll(String userId);
}
