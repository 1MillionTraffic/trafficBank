package com.trafficbank.trafficbank.repository;

import com.trafficbank.trafficbank.model.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findByUserId(Long userId);

    List<BankAccount> findAllByUserId(Long userId);

    List<BankAccount> findAllByUserIdIn(List<Long> allUser);
}
