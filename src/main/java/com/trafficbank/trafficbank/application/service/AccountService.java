package com.trafficbank.trafficbank.application.service;

import com.trafficbank.trafficbank.application.inPort.SendMoneyCommand;
import com.trafficbank.trafficbank.application.inPort.SendMoneyUseCase;
import com.trafficbank.trafficbank.application.outPort.LoadAccountPort;
import com.trafficbank.trafficbank.application.outPort.SaveAccountPort;
import com.trafficbank.trafficbank.application.outPort.SaveActivityPort;
import com.trafficbank.trafficbank.domain.Account;
import com.trafficbank.trafficbank.domain.Activity;
import com.trafficbank.trafficbank.domain.ActivityType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AccountService implements SendMoneyUseCase {
    private final LoadAccountPort loadAccountPort;
    private final SaveActivityPort saveActivityPort;
    private final SaveAccountPort saveAccountPort;

    @Transactional
    public boolean sendMoney(SendMoneyCommand sendMoneyCommand){
        Instant now = Instant.now();
        Account sourceAccount = loadAccountPort.loadAccount(sendMoneyCommand.getSourceAccountNumber());
        Account targetAccount = loadAccountPort.loadAccount(sendMoneyCommand.getTargetAccountNumber());
        // Question: 거래 이체 내역은 Async로 저장해도 괜찮을까?
        withdraw(sourceAccount, sendMoneyCommand.getMoney(), targetAccount, now);
        deposit(targetAccount, sendMoneyCommand.getMoney(), sourceAccount, now);
        return true;
    }

    @Transactional
    public void withdraw(Account oneAccount, Long money, Account otherAccount, Instant now){
        oneAccount.withdraw(money, otherAccount.getAccountId());
        Activity withdrawActivity = Activity.builder()
                .oneAccountId(oneAccount.getAccountId())
                .money(money)
                .activityType(ActivityType.WITHDRAW)
                .otherAccountId(otherAccount.getAccountId())
                .createDt(now)
                .build();
        saveAccountPort.saveAccount(oneAccount);
        saveActivityPort.saveActivity(withdrawActivity);
    }

    @Transactional
    public void deposit(Account oneAccount, Long money, Account otherAccount, Instant now){
        oneAccount.deposit(money, otherAccount.getAccountId());
        Activity depositActivity = Activity.builder()
                .oneAccountId(oneAccount.getAccountId())
                .money(money)
                .activityType(ActivityType.DEPOSIT)
                .otherAccountId(otherAccount.getAccountId())
                .createDt(now)
                .build();
        saveAccountPort.saveAccount(oneAccount);
        saveActivityPort.saveActivity(depositActivity);
    }

}
