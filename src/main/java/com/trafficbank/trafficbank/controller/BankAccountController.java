package com.trafficbank.trafficbank.controller;

import com.trafficbank.trafficbank.model.dto.BankAccountDTO;
import com.trafficbank.trafficbank.model.enums.BankType;
import com.trafficbank.trafficbank.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @GetMapping
    public List<BankAccountDTO> getAllBankAccount() {
        return bankAccountService.getAllBankAccount().stream()
                .map(BankAccountDTO::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public List<BankAccountDTO> getBankAccount(@PathVariable Long userId) {
        return bankAccountService.getBankAccount(userId).stream()
                .map(BankAccountDTO::of)
                .collect(Collectors.toList());
    }

    @PostMapping
    public BankAccountDTO createBankAccount(@RequestParam("user_id") Long userId, @RequestParam("account_name") String accountName, @RequestParam("bank_type") BankType bankType) {
        return BankAccountDTO.of(bankAccountService.createBankAccount(userId, accountName, bankType));
    }

    @DeleteMapping
    public String deleteBankAccount(Long bankAccountId) {
        bankAccountService.deleteBankAccount(bankAccountId);
        return "ok";
    }

}
