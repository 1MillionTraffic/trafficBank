package com.trafficbank.trafficbank.controller;

import com.trafficbank.trafficbank.model.dto.TransactionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/transaction")
@RestController
@RequiredArgsConstructor
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;

    @PostMapping
    public List<TransactionResult> transferMoney(@RequestParam("from_account_id") Long fromBankAccountId,
                                                 @RequestParam("to_account_id") Long toBankAccountId,
                                                 long money) {
        if (money <= 0) {
            throw new IllegalStateException("Money is not natural number.");
        }

        return transactionHistoryService.createTransactionHistory(fromBankAccountId, toBankAccountId, money);
    }

    @GetMapping
    public List<TransactionResult> getAllTransaction() {
        return transactionHistoryService.getAllTransaction();
    }

    @GetMapping("/{accountId}")
    public List<TransactionResult> getAllTransaction(@PathVariable Long accountId) {
        return transactionHistoryService.getAllTransaction(accountId);
    }

    @PostMapping("/{accountId}/withdraw")
    public TransactionResult withdrawAccount(@PathVariable Long accountId, long money) {
        if (money <= 0) {
            throw new IllegalStateException("Money is not natural number.");
        }

        return transactionHistoryService.withdraw(accountId, money);
    }

    @PostMapping("/{accountId}/deposit")
    public TransactionResult depositAccount(@PathVariable Long accountId, long money) {
        if (money <= 0) {
            throw new IllegalStateException("Money is not natural number.");
        }

        return transactionHistoryService.deposit(accountId, money);
    }

}
