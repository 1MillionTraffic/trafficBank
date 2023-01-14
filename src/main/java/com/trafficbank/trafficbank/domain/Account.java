package com.trafficbank.trafficbank.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/*
account: 계좌
balance: 잔액
deposit: 입금
withdraw: 출금
transfer: 이체
credited: 입금
debited: 인출
* */

@Getter
@Builder
@AllArgsConstructor
public class Account {
    private final Long id;
    private final Money baseBalance;
    private final ActivityWindow activityWindow;


    public boolean deposit(Money money, Long sourceAccountId){
        Activity deposit = Activity.builder()
                .ownerAccountId(this.id)
                .sourceAccountId(sourceAccountId)
                .targetAccountId(this.id)
                .createdDate(LocalDateTime.now())
                .money(money)
                .build();
        this.activityWindow.addActivity(deposit);
        return true;
    }

    public boolean withdraw(Money money, Long targetAccountId){
        if(!mayWithdraw(money)){
            return false;
        }
        Activity withdraw = Activity.builder()
                .ownerAccountId(this.id)
                .sourceAccountId(this.id)
                .targetAccountId(targetAccountId)
                .createdDate(LocalDateTime.now())
                .money(money)
                .build();
        this.activityWindow.addActivity(withdraw);
        return true;
    }

    private boolean mayWithdraw(Money money){
        return Money.subtract(this.calculateBalance(), money).isPositiveOrZero();
    }

    public Money calculateBalance(){
        return Money.add(this.baseBalance, this.activityWindow.calculateBalance(this.id));
    }
}
