package com.trafficbank.trafficbank.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BankCacheKey {

    DEPOSIT_AND_WITHDRAWAL_ACCOUNT("depositAndWithdrawalAccount", 100000000),
    SAVINGS_ACCOUNT("savingsAccount", 100000000),
    DEPOSIT_ACCOUNT("depositAccount", 100000000);

    private final String key;
    private final long defaultSequenceNumber;
}
