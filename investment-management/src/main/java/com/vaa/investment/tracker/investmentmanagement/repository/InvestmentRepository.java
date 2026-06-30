package com.vaa.investment.tracker.investmentmanagement.repository;

import com.vaa.investment.tracker.investmentmanagement.entity.Investment;
import com.vaa.investment.tracker.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {

    List<Investment> findByUserAndActiveTrue(User user);
    @Query("""
    SELECT COALESCE(SUM(i.investedAmount),0)
    FROM Investment i
    WHERE i.user.id = :userId AND i.active = true
""")
    BigDecimal totalInvested(@Param("userId") Long userId);

    @Query("""
    SELECT COALESCE(SUM(i.currentValue),0)
    FROM Investment i
    WHERE i.user.id = :userId AND i.active = true
""")
    BigDecimal totalCurrentValue(@Param("userId") Long userId);

    @Query("""
    SELECT COUNT(i)
    FROM Investment i
    WHERE i.user.id = :userId
      AND i.status = 'ACTIVE'
      AND i.active = true
""")
    Long activeCount(@Param("userId") Long userId);


}

