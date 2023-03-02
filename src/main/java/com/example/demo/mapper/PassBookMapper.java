package com.example.demo.mapper;

import com.example.demo.domain.entity.passbook.domain.PassBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface PassBookMapper {
    int add(PassBook passBook);
    int addReservation(PassBook passBook);

    List<PassBook> selectAll(String userId, int pbKindsNum);
    int selectAllCount(String userId, int pbKindsNum);
    int delete(int num, String userId);
    int updateReservation(int num, String userId, int month);
    List<PassBook> selectDaily(String userId, int year, int month, int day, int pbKindsNum);

    List<PassBook> selectMonthly(String userId, int year, int month, int pbKindsNum);

    List<PassBook> selectYearly(String userId, int year, int pbKindsNum);

    List<PassBook> selectTotalByDayList(String userId, int year, int month, int pbKindsNum);

    long selectDepositForMonthly(String userId, int year, int month, int pbKindsNum);
    long selectDepositForYearly(String userId, int year, int pbKindsNum);
    long selectWithdrawalForMonthly(String userId, int year, int month, int pbKindsNum);
    long selectWithdrawalForYearly(String userId, int year, int pbKindsNum);
    long selectTotalByMonth(String userId, int year, int month, int pbKindsNum);
    long selectTotalByYear(String userId, int year, int pbKindsNum);

    List<PassBook> selectMaxDepositForMonth(String userId, int year, int month, int pbKindsNum);

    List<PassBook> selectMaxDepositForYear(String userId, int year, int pbKindsNum);

    List<PassBook> selectMaxWithdrawalForMonth(String userId, int year, int month, int pbKindsNum);

    List<PassBook> selectMaxWithdrawalForYear(String userId, int year, int pbKindsNum);
    List<PassBook> selectReservationAll(String userId, int pbKindsNum);

    List<PassBook> selectReservation(String userId, int month, int pbKindsNum);
    PassBook selectEachReservationList(int num, String userId, int pbKindsNum);

}
