package com.trafficbank.trafficbank.controller;

import com.trafficbank.trafficbank.anootation.ShortLocker;
import com.trafficbank.trafficbank.model.dto.TransactionResult;
import com.trafficbank.trafficbank.service.TransactionHistoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/transaction")
@RestController
@RequiredArgsConstructor
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;

    @ShortLocker(key = "accountId={0}")
    @ShortLocker(key = "accountId={1}")
    @PostMapping
    public List<TransactionResult> transferMoney(@RequestParam("from_account_id") Long fromBankAccountId,
                                                 @RequestParam("to_account_id") Long toBankAccountId,
                                                 @Valid @Min(1) long money) {
        return transactionHistoryService.transfer(fromBankAccountId, toBankAccountId, money);
    }

    @GetMapping
    public List<TransactionResult> getAllTransaction() {
        return transactionHistoryService.getAllTransaction();
    }

    @GetMapping("/{accountId}")
    public List<TransactionResult> getTransaction(@PathVariable Long accountId) {
        return transactionHistoryService.getTransaction(accountId);
    }

    @ShortLocker(key = "accountId={0}")
    @PostMapping("/{accountId}/withdraw")
    public TransactionResult withdrawAccount(@PathVariable Long accountId, @Valid @Min(1) long money) {
        return transactionHistoryService.withdraw(accountId, money);
    }

    @ShortLocker(key = "accountId={0}")
    @PostMapping("/{accountId}/deposit")
    public TransactionResult depositAccount(@PathVariable Long accountId, @Valid @Min(1) long money) {
        return transactionHistoryService.deposit(accountId, money);
    }

}
