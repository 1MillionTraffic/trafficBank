package com.trafficbank.trafficbank.model.dto;

import com.trafficbank.trafficbank.model.entity.BankAccount;
import lombok.Builder;

import java.time.Instant;

@Builder
public record BankAccountDTO(Long id, Long userId, String accountSeq, String accountName,
                             Instant createdDt, Instant updatedDt) {
    public static BankAccountDTO of(BankAccount bankAccount) {
        return BankAccountDTO.builder()
                .id(bankAccount.getId())
                .userId(bankAccount.getUserId())
                .accountSeq(bankAccount.getAccountSeq())
                .accountName(bankAccount.getAccountName())
                .createdDt(bankAccount.getCreatedDt())
                .updatedDt(bankAccount.getUpdatedDt())
                .build();
    }
}
