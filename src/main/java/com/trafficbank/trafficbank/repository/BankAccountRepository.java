package com.trafficbank.trafficbank.repository;

import com.trafficbank.trafficbank.model.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    BankAccount findByUserId(Long userId);

    List<BankAccount> findAllByUserId(Long userId);

}
