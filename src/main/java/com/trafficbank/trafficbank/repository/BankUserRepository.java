package com.trafficbank.trafficbank.repository;

import com.trafficbank.trafficbank.model.entity.BankUser;
import com.trafficbank.trafficbank.model.enums.BankUserState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankUserRepository extends JpaRepository<BankUser, Long> {
    List<BankUser> findAllByState(BankUserState normal, Pageable pageable);
}
