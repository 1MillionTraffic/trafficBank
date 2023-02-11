package com.trafficbank.trafficbank.repository;

import com.trafficbank.trafficbank.entity.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    /*
    * PESSIMISTIC_READ:
    * PESSIMISTIC_WRITE: dirty read, non-repeatable read 불가
    * */
    @Lock(LockModeType.PESSIMISTIC_WRITE) // 비관적 락
    @Query(value = "select a from Account a " +
        "where a.number = :number"
    )
    Optional<Account> findByNumber(@Param("number") String number);
}
