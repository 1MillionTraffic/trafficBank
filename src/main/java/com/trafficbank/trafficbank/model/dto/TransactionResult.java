package com.trafficbank.trafficbank.model.dto;

import com.trafficbank.trafficbank.model.entity.TransactionHistory;
import lombok.Builder;

import java.time.Instant;

@Builder
public record TransactionResult(String transactionSeq, Long money, Long balance, Long fromAccountId, Long toAccountId,
                                Instant createdDt) {
    public static TransactionResult of(TransactionHistory transactionHistory) {
        return TransactionResult.builder()
                .transactionSeq(transactionHistory.getTransactionSeq())
                .money(transactionHistory.getMoney())
                .balance(transactionHistory.getBalance())
                .fromAccountId(transactionHistory.getFromAccountId())
                .toAccountId(transactionHistory.getToAccountId())
                .createdDt(transactionHistory.getCreatedDt())
                .build();
    }
}
