package com.trafficbank.trafficbank.repository;

import com.trafficbank.trafficbank.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityJpaRepository extends JpaRepository<ActivityEntity, Long> {
    @Query("select a from ActivityEntity as a " +
            "where a.ownerAccountId = :ownerAccountId " +
            "and a.createdDate >= :createdDate"
    )
    List<ActivityEntity> findByOwnerIdAfterCreatedDate(
            @Param("ownerAccountId")Long ownerAccountId,
            @Param("createdDate") LocalDateTime createdDate);

    @Query("select sum(a.amount) from ActivityEntity as a " +
            "where a.ownerAccountId = :accountId " +
            "and a.targetAccountId = :accountId " +
            "and a.createdDate < :createdDate"
    )
    Long getDepositBalanceBeforeCreatedDate(
            @Param("accountId") Long accountId,
            @Param("createdDate") LocalDateTime createdDate
    );

    @Query("select sum(a.amount) from ActivityEntity as a " +
            "where a.ownerAccountId = :accountId " +
            "and a.sourceAccountId = :accountId " +
            "and a.createdDate < :createdDate"
    )
    Long getWithdrawBalanceBeforeCreatedDate(
            @Param("accountId") Long accountId,
            @Param("createdDate") LocalDateTime createdDate
    );
}
