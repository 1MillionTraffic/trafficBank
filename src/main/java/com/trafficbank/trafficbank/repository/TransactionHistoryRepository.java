package com.trafficbank.trafficbank.repository;

import com.trafficbank.trafficbank.model.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    Optional<TransactionHistory> findFirstByFromAccountIdOrderByIdDesc(Long fromAccountId);

    List<TransactionHistory> findAllByFromAccountId(Long accountId);

    List<TransactionHistory> findAllByCreatedDtBetween(Instant from, Instant to);

    List<TransactionHistory> findAllByIdIn(List<Long> idList);

}
