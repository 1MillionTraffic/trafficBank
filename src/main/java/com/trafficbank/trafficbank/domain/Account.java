package com.trafficbank.trafficbank.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class Account {
    private Long accountId;
    private String accountNumber;
    private Long balance;
    private Instant createDt;
    private Instant modifiedDt;

    @Builder
    public Account(Long accountId, String accountNumber, Long balance, Instant createDt, Instant modifiedDt) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.createDt = createDt;
        this.modifiedDt = modifiedDt;
    }

    // 나에게서 targetAccountId 로 돈을 줄거야
    public void withdraw(Long money, Long targetAccountId){
        if(canWithdraw(money)){
            this.balance -= money;
        }
    }
    
    // sourceAccountId 로부터 나에게 돈을 넣을거야
    public void deposit(Long money, Long sourceAccountId){
        this.balance += money;
    }
    
    private boolean canWithdraw(Long money){
        return this.balance - money >= 0;
    }
}
