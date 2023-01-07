package com.trafficbank.trafficbank.service;

import com.trafficbank.trafficbank.model.entity.BankAccount;
import com.trafficbank.trafficbank.model.enums.BankCacheKey;
import com.trafficbank.trafficbank.model.enums.BankType;
import com.trafficbank.trafficbank.repository.BankAccountRepository;
import com.trafficbank.trafficbank.repository.BankUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final LettuceService lettuceService;
    private final BankAccountRepository bankAccountRepository;
    private final BankUserRepository bankUserRepository;

    public List<BankAccount> getAllBankAccount() {
        return bankAccountRepository.findAll();
    }

    public List<BankAccount> getBankAccount(Long userId) {
        return bankAccountRepository.findAllByUserId(userId);
    }

    public BankAccount createBankAccount(Long userId, String accountName, BankType bankType) {
        bankUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User is not exist."));

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankType(bankType.getType());
        bankAccount.setAccountSeq(generateAccountSeq(bankType));
        bankAccount.setUserId(userId);
        bankAccount.setAccountName(accountName);

        bankAccountRepository.save(bankAccount);

        return bankAccount;
    }

    public void deleteBankAccount(Long bankAccountId) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new IllegalStateException("Account is not exist."));

        bankAccountRepository.delete(bankAccount);
    }

    private String generateAccountSeq(BankType bankType) {
        BankCacheKey bankCacheKey = bankType.getBankCacheKey();
        String sequenceNumber = String.valueOf(
                lettuceService.getSequenceNumber(bankCacheKey.getKey(), bankCacheKey.getDefaultSequenceNumber())
        );

        return bankType.getType() + sequenceNumber;
    }
}
