package com.trafficbank.trafficbank.service;

import com.trafficbank.trafficbank.adapter.AccountPersistenceAdapter;
import com.trafficbank.trafficbank.domain.Account;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SendMoneyService {
    private final AccountPersistenceAdapter accountPersistenceAdapter;

    @Transactional
    public boolean sendMoney(SendMoneyCommand command){
        LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);
        Account sourceAccount =  accountPersistenceAdapter.getAccount(
                command.getSourceAccountId(),
                baselineDate
        );
        Account targetAccount = accountPersistenceAdapter.getAccount(
                command.getTargetAccountId(),
                baselineDate
        );
        if(!sourceAccount.withdraw(command.getMoney(), targetAccount.getId())){
            return false;
        }

        if(!targetAccount.deposit(command.getMoney(), sourceAccount.getId())){
            return false;
        }
        accountPersistenceAdapter.saveActivities(sourceAccount);
        accountPersistenceAdapter.saveActivities(targetAccount);
        return true;
    }
}
