package com.trafficbank.trafficbank.service;

import com.trafficbank.trafficbank.adapter.AccountPersistenceAdapter;
import com.trafficbank.trafficbank.domain.Money;
import com.trafficbank.trafficbank.repository.AccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GetBalanceService {
    private final AccountPersistenceAdapter accountPersistenceAdapter;

    public Money getBalance(Long accountId){
        return accountPersistenceAdapter.getAccount(accountId, LocalDateTime.now()).calculateBalance();
    }
}
