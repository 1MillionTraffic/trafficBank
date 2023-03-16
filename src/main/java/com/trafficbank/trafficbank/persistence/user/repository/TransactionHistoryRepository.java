package com.trafficbank.trafficbank.persistence.user.repository;

import com.trafficbank.trafficbank.model.enums.TransactionStatus;
import com.trafficbank.trafficbank.persistence.user.entity.TransactionHistory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    Optional<TransactionHistory> findFirstByFromAccountIdOrderByIdDesc(Long fromAccountId);

    List<TransactionHistory> findAllByFromAccountId(Long accountId);

    List<TransactionHistory> findAllByCreatedDtBetween(Instant from, Instant to);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<TransactionHistory> findAllByIdInAndTransactionStatus(List<Long> idList, TransactionStatus status);

}
