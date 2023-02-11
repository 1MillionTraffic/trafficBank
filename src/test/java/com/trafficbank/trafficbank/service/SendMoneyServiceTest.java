package com.trafficbank.trafficbank.service;

import com.trafficbank.trafficbank.entity.Account;
import com.trafficbank.trafficbank.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SendMoneyServiceTest {
    private static final String FROM_NUMBER = "1";
    private static final String TO_NUMBER = "2";
    private static final long BALANCE = 10L;
    private static final long MONEY = 1L;
    private static final int N_THREADS = 2;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SendMoneyService sendMoneyService;


    @Test
    public void 동시성테스트() throws InterruptedException{
        Account fromAccount = Account.builder()
                .balance(BALANCE)
                .number(FROM_NUMBER)
                .build();
        Account toAccount = Account.builder()
                .balance(BALANCE)
                .number(TO_NUMBER)
                .build();
        Long fromId = accountRepository.save(fromAccount).getId();
        Long toId = accountRepository.save(toAccount).getId();

        ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);
        CountDownLatch latch = new CountDownLatch(N_THREADS);

        for(int i=0; i < N_THREADS; i++){
            executorService.execute(() -> {
                sendMoneyService.send(FROM_NUMBER, TO_NUMBER, MONEY);
                latch.countDown();
            });
        }

        latch.await();
        System.out.println("[start] test select account");
        Account account = accountRepository.findById(fromId)
                .orElseThrow(() -> new IllegalArgumentException(""));
        assertEquals(BALANCE-2*MONEY, account.getBalance());
    }
}
