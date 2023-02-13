package com.trafficbank.trafficbank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionMessage {
    private long fromBankAccountId;
    private long toBankAccountId;
    private long money;
    private long fromTransactionId;
    private long toTransactionId;
}
