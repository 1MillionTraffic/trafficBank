package com.trafficbank.trafficbank.service;

import com.trafficbank.trafficbank.model.dto.BankAccountDTO;
import com.trafficbank.trafficbank.model.dto.BankUserDTO;
import com.trafficbank.trafficbank.repository.BankAccountRepository;
import com.trafficbank.trafficbank.repository.BankUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntityService {
    private final BankAccountRepository bankAccountRepository;
    private final BankUserRepository bankUserRepository;

    public List<BankAccountDTO> getAllBankAccount() {
        return bankAccountRepository.findAll().stream()
                .map(BankAccountDTO::of)
                .collect(Collectors.toList());
    }

    public List<BankAccountDTO> getBankAccount(Long userId) {
        return bankAccountRepository.findAllByUserId(userId).stream()
                .map(BankAccountDTO::of)
                .collect(Collectors.toList());
    }

    public List<BankUserDTO> getAllBankUser() {
        return bankUserRepository.findAll().stream()
                .map(BankUserDTO::of)
                .collect(Collectors.toList());
    }
}
