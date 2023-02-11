package com.trafficbank.trafficbank.adapter.in.web;

import com.trafficbank.trafficbank.adapter.in.web.dto.SendRequestDto;
import com.trafficbank.trafficbank.application.inPort.SendMoneyCommand;
import com.trafficbank.trafficbank.application.inPort.SendMoneyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/accounts")
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final SendMoneyUseCase sendMoneyUseCase;

    @PostMapping
    public boolean send(@RequestBody SendRequestDto requestDto) throws IllegalAccessException {
        SendMoneyCommand command = new SendMoneyCommand(
                requestDto.getSourceAccountNumber(),
                requestDto.getTargetAccountNumber(),
                requestDto.getMoney());
        return sendMoneyUseCase.sendMoney(command);
    }
}
