package com.trafficbank.trafficbank.repository;

import com.trafficbank.trafficbank.model.entity.BankAccount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<BankAccount> findWithPessimisticLockById(Long id);

    Optional<BankAccount> findByUserId(Long userId);

    List<BankAccount> findAllByUserId(Long userId);

    List<BankAccount> findAllByUserIdIn(List<Long> allUser);
}
