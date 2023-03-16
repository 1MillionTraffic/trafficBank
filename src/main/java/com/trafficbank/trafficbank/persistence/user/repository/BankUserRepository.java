package com.trafficbank.trafficbank.persistence.user.repository;

import com.trafficbank.trafficbank.model.enums.BankUserState;
import com.trafficbank.trafficbank.persistence.user.entity.BankUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankUserRepository extends JpaRepository<BankUser, Long> {
    List<BankUser> findAllByState(BankUserState normal, Pageable pageable);
}
