package com.trafficbank.trafficbank.controller;

import com.trafficbank.trafficbank.dto.request.CreateAccountRequestDto;
import com.trafficbank.trafficbank.dto.request.SendRequestDto;
import com.trafficbank.trafficbank.dto.response.AccountResponseDto;
import com.trafficbank.trafficbank.service.CreateAccountService;
import com.trafficbank.trafficbank.service.SendMoneyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/accounts")
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final CreateAccountService createAccountService;
    private final SendMoneyService sendMoneyService;

    @GetMapping("/test")
    public String test(){
        return "hello";
    }

    @PostMapping()
    public AccountResponseDto createAccount(@RequestBody CreateAccountRequestDto requestDto){
        return createAccountService.createAccount(requestDto);
    }

    @PutMapping("/send")
    public AccountResponseDto send(@RequestBody SendRequestDto requestDto){
        return sendMoneyService.send(requestDto.getFromNumber(), requestDto.getToNumber(), requestDto.getMoney());
    }
}
