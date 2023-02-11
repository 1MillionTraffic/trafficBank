package com.trafficbank.trafficbank.adapter.out.persistence;

import com.trafficbank.trafficbank.domain.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account toAccount(AccountEntity accountEntity){
        return Account.builder()
                .accountId(accountEntity.getAccountId())
                .accountNumber(accountEntity.getAccountNumber())
                .balance(accountEntity.getBalance())
                .createDt(accountEntity.getCreatedDt())
                .modifiedDt(accountEntity.getModifiedDt())
                .build();
    }

    public AccountEntity toAccountEntity(Account account){
        return AccountEntity.builder()
                .accountId(account.getAccountId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .createdDt(account.getCreateDt())
                .modifiedDt(account.getModifiedDt())
                .build();
    }
}
