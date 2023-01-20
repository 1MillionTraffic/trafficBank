package com.trafficbank.trafficbank.service;

import com.trafficbank.trafficbank.dto.request.CreateAccountRequestDto;
import com.trafficbank.trafficbank.dto.response.AccountResponseDto;
import com.trafficbank.trafficbank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CreateAccountService {
    private final AccountRepository accountRepository;

    @Transactional
    public AccountResponseDto createAccount(CreateAccountRequestDto requestDto){
        return AccountResponseDto.of(accountRepository.save(requestDto.toEntity()));
    }
}
