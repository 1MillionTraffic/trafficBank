package com.trafficbank.trafficbank.controller;

import com.trafficbank.trafficbank.model.dto.BankAccountDTO;
import com.trafficbank.trafficbank.model.dto.BankUserDTO;
import com.trafficbank.trafficbank.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/entity")
@RestController
@RequiredArgsConstructor
public class EntityController {

    private final EntityService entityService;

    @GetMapping("/bank-user")
    public List<BankUserDTO> getAllBankUser() {
        return entityService.getAllBankUser();
    }

    @GetMapping("/bank-account")
    public List<BankAccountDTO> getAllBankAccount() {
        return entityService.getAllBankAccount();
    }

    @GetMapping("/bank-account/{userId}")
    public List<BankAccountDTO> getBankAccount(@PathVariable Long userId) {
        return entityService.getBankAccount(userId);
    }

}
