package com.trafficbank.trafficbank.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BankType {
    DEPOSIT_AND_WITHDRAWAL_ACCOUNT("3333", BankCacheKey.DEPOSIT_AND_WITHDRAWAL_ACCOUNT),
    SAVINGS_ACCOUNT("3388", BankCacheKey.SAVINGS_ACCOUNT),
    DEPOSIT_ACCOUNT("3355", BankCacheKey.DEPOSIT_ACCOUNT);

    private final String type;
    private final BankCacheKey bankCacheKey;
}
