package com.trafficbank.trafficbank.application.inPort;

import lombok.Getter;

@Getter
public class SendMoneyCommand {
    private final String sourceAccountNumber;
    private final String targetAccountNumber;
    private final Long money;

    public SendMoneyCommand(String sourceAccountNumber, String targetAccountNumber, Long money) throws IllegalAccessException {
        check(sourceAccountNumber, targetAccountNumber, money);
        this.sourceAccountNumber = sourceAccountNumber;
        this.targetAccountNumber = targetAccountNumber;
        this.money = money;
    }

    private void check(String sourceAccountNumber, String targetAccountNumber, Long money) throws IllegalAccessException {
        boolean flag = sourceAccountNumber != null;
        if (targetAccountNumber == null){
            flag = false;
        }
        if(!(money != null && money > 0)){
            flag = false;
        }
        if(!flag){
            throw new IllegalAccessException("");
        }
    }
}
