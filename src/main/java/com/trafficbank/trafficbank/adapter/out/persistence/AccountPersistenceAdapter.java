package com.trafficbank.trafficbank.adapter.out.persistence;

import com.trafficbank.trafficbank.application.outPort.LoadAccountPort;
import com.trafficbank.trafficbank.application.outPort.SaveAccountPort;
import com.trafficbank.trafficbank.application.outPort.SaveActivityPort;
import com.trafficbank.trafficbank.domain.Account;
import com.trafficbank.trafficbank.domain.Activity;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class AccountPersistenceAdapter implements LoadAccountPort, SaveAccountPort {
    private final AccountJpaRepository accountRepository;

    private final AccountMapper accountMapper;

    @Override
    public Account loadAccount(String accountNumber) {
        AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber).orElseThrow(IllegalArgumentException::new);
        return accountMapper.toAccount(accountEntity);
    }

    @Override
    public void saveAccount(Account account) {
        AccountEntity accountEntity = accountMapper.toAccountEntity(account);
        accountRepository.save(accountEntity);
    }

}
