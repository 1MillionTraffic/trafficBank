package com.trafficbank.trafficbank.model.dto;

import com.trafficbank.trafficbank.model.enums.TransactionType;
import com.trafficbank.trafficbank.persistence.user.entity.TransactionHistory;
import lombok.Builder;

import java.time.Instant;

@Builder
public record TransactionResult(String transactionSeq, Long money, Long balance, Long fromAccountId, Long toAccountId,
                                TransactionType transactionType, Instant createdDt) {
    public static TransactionResult of(TransactionHistory transactionHistory) {
        return TransactionResult.builder()
                .transactionSeq(transactionHistory.getTransactionSeq())
                .money(transactionHistory.getMoney())
                .balance(transactionHistory.getBalance())
                .fromAccountId(transactionHistory.getFromAccountId())
                .toAccountId(transactionHistory.getToAccountId())
                .transactionType(transactionHistory.getTransactionType())
                .createdDt(transactionHistory.getCreatedDt())
                .build();
    }
}
