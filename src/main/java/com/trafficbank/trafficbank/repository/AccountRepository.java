package com.trafficbank.trafficbank.repository;

import com.trafficbank.trafficbank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query(value = "select * from accounts a " +
        "where a.number = :number",
            nativeQuery = true
    )
    Optional<Account> findByNumber(@Param("number") String number);
}
