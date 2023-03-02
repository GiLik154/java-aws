package com.example.demo.controller.vo;

import com.example.demo.domain.entity.passbook.service.delete.dto.DateServiceDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DateVoTest {

    @Test
    void 출력_날짜() {
        //given
        LocalDate now = LocalDate.now();
        DateVo dateVo = new DateVo(String.valueOf(now));

        //when
        String testDate = dateVo.getDate();

        //then
        Assertions.assertThat(testDate).isEqualTo(String.valueOf(now));
    }

    @Test
    void 출력_년도() {
        //given
        LocalDate now = LocalDate.now();
        DateVo dateVo = new DateVo(String.valueOf(now));

        //when
        int testYear = dateVo.getYear();

        //then
        Assertions.assertThat(testYear).isEqualTo(now.getYear());
    }

    @Test
    void 출력_월() {
        //given
        LocalDate now = LocalDate.now();
        DateVo dateVo = new DateVo(String.valueOf(now));

        //when
        int testMonth = dateVo.getMonth();

        //then
        Assertions.assertThat(testMonth).isEqualTo(now.getMonthValue());
    }

    @Test
    void 출력_일() {
        //given
        LocalDate now = LocalDate.now();
        DateVo dateVo = new DateVo(String.valueOf(now));

        //when
        int testDay = dateVo.getDay();

        //then
        Assertions.assertThat(testDay).isEqualTo(now.getDayOfMonth());
    }

    @Test
    void DTO_컨버터() {
        //given
        LocalDate now = LocalDate.now();
        DateVo dateVo = new DateVo(String.valueOf(now));

        //when
        DateServiceDto dateServiceDto = dateVo.convertedDto();
        int testYear = dateServiceDto.getYear();
        int testMonth = dateServiceDto.getMonth();
        int testDay = dateServiceDto.getDay();

        //then
        Assertions.assertThat(testYear).isEqualTo(now.getYear());
        Assertions.assertThat(testMonth).isEqualTo(now.getMonthValue());
        Assertions.assertThat(testDay).isEqualTo(now.getDayOfMonth());
    }

    @Test
    void findMaxDay() {
        //given
        LocalDate now = LocalDate.now();
        DateVo dateVo = new DateVo(String.valueOf(now));

        //when
        int testMaxDay = dateVo.findMaxDay();

        //then
        Assertions.assertThat(testMaxDay).isEqualTo(now.lengthOfMonth());
    }

    @Test
    void 이번달_1일의_첫요일_찾기() {
        //given
        LocalDate now = LocalDate.now();
        LocalDate findFirstDay = LocalDate.of(now.getYear(), now.getMonthValue(), 2);

        DayOfWeek dayOfWeek = findFirstDay.getDayOfWeek();

        DateVo dateVo = new DateVo(String.valueOf(now));

        //when
        int testFirstDayOfWeek = dateVo.findFirstDayOfWeek();

        //then
        Assertions.assertThat(testFirstDayOfWeek).isEqualTo(dayOfWeek.getValue());
    }

    @Test
    void 년도_월_합치기() {
        //given
        LocalDate now = LocalDate.now();
        DateVo dateVo = new DateVo(String.valueOf(now));

        //when
        String YearAndMonth = dateVo.sumYearAndMonth();

        //then
        Assertions.assertThat(YearAndMonth).isEqualTo(now.getYear() + "-" + now.getMonthValue());
    }
}