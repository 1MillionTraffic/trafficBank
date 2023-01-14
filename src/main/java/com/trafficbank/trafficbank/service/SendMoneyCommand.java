package com.trafficbank.trafficbank.service;

import com.trafficbank.trafficbank.domain.Money;
import lombok.Getter;

@Getter
public class SendMoneyCommand {
    private Long sourceAccountId;
    private Long targetAccountId;
    private Money money;

    public SendMoneyCommand(
            Long sourceAccountId,
            Long targetAccountId,
            Money money
    ){
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
    }
}
