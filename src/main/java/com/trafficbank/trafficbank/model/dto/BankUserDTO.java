package com.trafficbank.trafficbank.model.dto;

import com.trafficbank.trafficbank.persistence.user.entity.BankUser;
import lombok.Builder;

import java.time.Instant;

@Builder
public record BankUserDTO(Long id, String seq, String name, Instant createdDt, Instant updatedDt) {
    public static BankUserDTO of(BankUser bankUser) {
        return BankUserDTO.builder()
                .id(bankUser.getId())
                .seq(bankUser.getSeq())
                .name(bankUser.getName())
                .createdDt(bankUser.getCreatedDt())
                .updatedDt(bankUser.getUpdatedDt())
                .build();
    }
}
