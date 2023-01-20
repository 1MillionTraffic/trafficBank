package com.trafficbank.trafficbank.service;

import com.trafficbank.trafficbank.dto.response.ActivityResponseDto;
import com.trafficbank.trafficbank.entity.Account;
import com.trafficbank.trafficbank.repository.AccountRepository;
import com.trafficbank.trafficbank.repository.ActivityRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@SpringBootTest
public class GetActivitiesServiceTest {
    public static final String FROM_NUMBER = "111";
    public static final String TO_NUMBER = "222";
    @Autowired
    private GetActivitiesService getActivitiesService;
    @Autowired
    private SendMoneyService sendMoneyService;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ActivityRepository activityRepository;

    public Account fromAccount;
    public Account toAccount;


    @BeforeAll
    public void beforeEach(){
        fromAccount = Account.builder()
                .balance(100000L)
                .number(FROM_NUMBER)
                .build();
        toAccount = Account.builder()
                .balance(100000L)
                .number(TO_NUMBER)
                .build();
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    @Test
    public void test(){
        sendMoneyService.send(fromAccount.getNumber(), toAccount.getNumber(), 100L);
        sendMoneyService.send(toAccount.getNumber(), fromAccount.getNumber(),  500L);
        List<ActivityResponseDto> list = getActivitiesService.findAll(fromAccount.getNumber());
        System.out.println(list);
    }
}
