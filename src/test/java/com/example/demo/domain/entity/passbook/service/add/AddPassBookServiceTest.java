package com.example.demo.domain.entity.passbook.service.add;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import com.example.demo.mapper.PassBookKindsMapper;
import com.example.demo.mapper.PassBookMapper;
import com.example.demo.domain.entity.passbook.service.add.dto.AddPassBookServiceDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AddPassBookServiceTest {
    private final AddPassBookService addPassBookService;
    private final PassBookMapper passBookMapper;
    private final PassBookKindsMapper passBookKindsMapper;

    @Autowired
    AddPassBookServiceTest(AddPassBookService addPassBookService, PassBookMapper passBookMapper, PassBookKindsMapper passBookKindsMapper) {
        this.addPassBookService = addPassBookService;
        this.passBookMapper = passBookMapper;
        this.passBookKindsMapper = passBookKindsMapper;
    }

    @Test
    void 글_작성_테스트_정상작동() {
        //given
        passBookKindsMapper.add("test", "test", "0000-1111-2222");
        int pbKindsNum = passBookKindsMapper.selectAllByUserId("test").get(0).getNum();

        AddPassBookServiceDto addPassBookServiceDto = new AddPassBookServiceDto("test", "입금", 10000, "테스트 입금", 2023, 1, 1, "일반", pbKindsNum);

        //when
        boolean isAdd = addPassBookService.add(addPassBookServiceDto);
        PassBook getPassBook = passBookMapper.selectAll("test", pbKindsNum).get(0);

        //then
        Assertions.assertThat(isAdd).isTrue();

        Assertions.assertThat(getPassBook.getUser()).isEqualTo(addPassBookServiceDto.getUser());
        Assertions.assertThat(getPassBook.getKinds()).isEqualTo(addPassBookServiceDto.getKinds());
        Assertions.assertThat(getPassBook.getPrice()).isEqualTo(addPassBookServiceDto.getPrice());
        Assertions.assertThat(getPassBook.getContent()).isEqualTo(addPassBookServiceDto.getContent());
        Assertions.assertThat(getPassBook.getYear()).isEqualTo(addPassBookServiceDto.getYear());
        Assertions.assertThat(getPassBook.getMonth()).isEqualTo(addPassBookServiceDto.getMonth());
        Assertions.assertThat(getPassBook.getDay()).isEqualTo(addPassBookServiceDto.getDay());
        Assertions.assertThat(getPassBook.getReservation()).isEqualTo(addPassBookServiceDto.getReservation());
    }

    @Test
    void 글_작성_테스트_NULL_전송() {
        //given
        PassBook passBook = new PassBook();

        //when
        Throwable thrown = catchThrowable(() -> passBookMapper.add(passBook));

        //then
        Assertions.assertThat(thrown).isInstanceOf(DataIntegrityViolationException.class);
    }

}