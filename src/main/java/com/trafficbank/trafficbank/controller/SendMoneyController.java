package com.trafficbank.trafficbank.controller;

import com.trafficbank.trafficbank.domain.Money;
import com.trafficbank.trafficbank.service.SendMoneyCommand;
import com.trafficbank.trafficbank.service.SendMoneyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SendMoneyController {
    private final SendMoneyService sendMoneyService;

    @PostMapping("/accounts/{sourceAccountId}/{targetAccountId}/{amount}")
    public void sendMoney(
            @PathVariable("sourceAccountId") Long sourceAccountId,
            @PathVariable("targetAccountId") Long targetAccountId,
            @PathVariable("amount") Long amount
    ){
        SendMoneyCommand command = new SendMoneyCommand(
                sourceAccountId,
                targetAccountId,
                Money.of(amount)
        );
        sendMoneyService.sendMoney(command);
    }
}
