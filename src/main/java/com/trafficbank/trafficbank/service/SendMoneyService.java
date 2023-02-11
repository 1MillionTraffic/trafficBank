package com.trafficbank.trafficbank.service;

import com.trafficbank.trafficbank.dto.response.AccountResponseDto;
import com.trafficbank.trafficbank.entity.Account;
import com.trafficbank.trafficbank.entity.Activity;
import com.trafficbank.trafficbank.entity.ActivityType;
import com.trafficbank.trafficbank.repository.AccountRepository;
import com.trafficbank.trafficbank.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SendMoneyService {
    private final AccountRepository accountRepository;
    private final ActivityRepository activityRepository;

    private Account findByNumber(String number){
        return accountRepository.findByNumber(number)
                .orElseThrow(() -> new IllegalArgumentException(""));
    }

    @Transactional
    public AccountResponseDto send(String fromNumber, String toNumber, Long money){
        System.out.println("[Tread] " + Thread.currentThread() + "[start] select fromAccount");
        Account fromAccount = findByNumber(fromNumber); // 10 원
        System.out.println("[Tread] " + Thread.currentThread() + "[end] select fromAccount" + "balance: "+fromAccount.getBalance());

        System.out.println("[Tread] " + Thread.currentThread() + "[start] select toAccount");
        Account toAccount = findByNumber(toNumber);
        System.out.println("[Tread] " + Thread.currentThread() + "[end] select toAccount" + "balance: "+fromAccount.getBalance());

        System.out.println("[Tread] " + Thread.currentThread() + "[start] withdraw");
        withdraw(money, fromAccount, toAccount);
        System.out.println("[Tread] " + Thread.currentThread() + "[end] withdraw" + "balance: "+fromAccount.getBalance());

        System.out.println("[Tread] " + Thread.currentThread() + "[start] deposit");
        deposit(money, fromAccount, toAccount);
        System.out.println("[Tread] " + Thread.currentThread() + "[end] deposit" + "balance: "+fromAccount.getBalance());
        return AccountResponseDto.of(fromAccount);
    }

    @Transactional
    public void deposit(Long money, Account fromAccount, Account toAccount) {
        toAccount.plusMoney(money);
        Activity toActivity = Activity.builder()
                .accountNumber(fromAccount.getNumber())
                .otherAccountNumber(toAccount.getNumber())
                .amount(money)
                .balance(toAccount.getBalance())
                .activityType(ActivityType.DEPOSIT)
                .build();
        activityRepository.save(toActivity);
    }
    @Transactional
    public void withdraw(Long money, Account fromAccount, Account toAccount) {
        fromAccount.minusMoney(money);
        Activity fromActivity = Activity.builder()
                .accountNumber(fromAccount.getNumber())
                .otherAccountNumber(toAccount.getNumber())
                .amount(money)
                .balance(fromAccount.getBalance())
                .activityType(ActivityType.WITHDRAW)
                .build();
        activityRepository.save(fromActivity);
    }


}
