package com.example.demo.domain.entity.passbookkinds.service.add;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AddPassBookKindsServiceImplTest {
    private final AddPassBookKindsService addPassBookKindsService;
    @Autowired
    public AddPassBookKindsServiceImplTest(AddPassBookKindsService addPassBookKindsService) {
        this.addPassBookKindsService = addPassBookKindsService;
    }

    @Test
    void 가계부_추가_정상작동() {
        //given
        //then
        boolean isAdd = addPassBookKindsService.add("test", "test");
        //when
        Assertions.assertThat(isAdd).isTrue();
    }
}