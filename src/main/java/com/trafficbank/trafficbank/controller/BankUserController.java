package com.trafficbank.trafficbank.controller;

import com.trafficbank.trafficbank.model.dto.BankUserDTO;
import com.trafficbank.trafficbank.service.BankUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class BankUserController {

    private final BankUserService bankUserService;

    @GetMapping("/{userId}")
    public BankUserDTO getBankUser(@PathVariable Long userId) {
        return BankUserDTO.of(bankUserService.getBankUser(userId));
    }

    @GetMapping
    public List<BankUserDTO> getAllBankUser() {
        return bankUserService.getAllBankUser().stream()
                .map(BankUserDTO::of)
                .toList();
    }

    @PostMapping
    public BankUserDTO createBankUser(String name) {
        return BankUserDTO.of(bankUserService.createBankUser(name));
    }

    @PutMapping
    public BankUserDTO updateBankUser(Long userId, String name) {
        return BankUserDTO.of(bankUserService.updateBankUser(userId, name));
    }

    @DeleteMapping
    public String deleteBankUser(Long userId) {
        bankUserService.deleteBankUser(userId);

        return "ok";
    }

}
