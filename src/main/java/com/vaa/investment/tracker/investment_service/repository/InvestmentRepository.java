package com.vaa.investment.tracker.investment_service.repository;

import com.vaa.investment.tracker.investment_service.entity.Investment;
import com.vaa.investment.tracker.investment_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {

    List<Investment> findByUserAndActiveTrue(User user);

}

