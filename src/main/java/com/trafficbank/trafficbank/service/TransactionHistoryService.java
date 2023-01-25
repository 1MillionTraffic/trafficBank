package com.trafficbank.trafficbank.service;

import com.trafficbank.trafficbank.model.dto.TransactionResult;
import com.trafficbank.trafficbank.model.entity.BankAccount;
import com.trafficbank.trafficbank.model.entity.TransactionHistory;
import com.trafficbank.trafficbank.model.enums.TransactionType;
import com.trafficbank.trafficbank.repository.BankAccountRepository;
import com.trafficbank.trafficbank.repository.TransactionHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;
    private final BankAccountRepository bankAccountRepository;

    @Transactional
    public List<TransactionResult> transfer(Long fromBankAccountId, Long toBankAccountId, long money) {
        BankAccount fromBankAccount = bankAccountRepository.findById(fromBankAccountId)
                .orElseThrow(() -> new IllegalStateException("Account is not exists."));

        BankAccount toBankAccount = bankAccountRepository.findById(toBankAccountId)
                .orElseThrow(() -> new IllegalStateException("Account is not exists."));

        // TODO: 정지 계좌인지 체크
        long fromLastBalance = fromBankAccount.getMoney();

        if (fromLastBalance < money) {
            throw new IllegalStateException("Balance is insufficient.");
        }

        long toLastBalance = toBankAccount.getMoney();

        List<TransactionHistory> transactionHistoryList = makeTransferTransactionHistoryList(fromBankAccountId, toBankAccountId, money, fromLastBalance, toLastBalance);
        transactionHistoryRepository.saveAll(transactionHistoryList);

        fromBankAccount.setMoney(fromLastBalance - money);
        toBankAccount.setMoney(fromLastBalance + money);
        bankAccountRepository.saveAll(List.of(fromBankAccount, toBankAccount));

        return transactionHistoryList.stream()
                .map(TransactionResult::of)
                .toList();
    }

    public List<TransactionResult> getAllTransaction() {
        return transactionHistoryRepository.findAll().stream()
                .map(TransactionResult::of)
                .toList();
    }

    private List<TransactionHistory> makeTransferTransactionHistoryList(Long fromBankAccountId, Long toBankAccountId, long money, Long fromLastBalance, Long toLastBalance) {
        String transactionSequence = UUID.randomUUID().toString();

        TransactionHistory fromTransactionHistory = new TransactionHistory();
        fromTransactionHistory.setTransactionSeq(transactionSequence);
        fromTransactionHistory.setFromAccountId(fromBankAccountId);
        fromTransactionHistory.setToAccountId(toBankAccountId);
        fromTransactionHistory.setMoney(-money);
        fromTransactionHistory.setBalance(fromLastBalance - money);
        fromTransactionHistory.setTransactionType(TransactionType.TRANSFER);

        TransactionHistory toTransactionHistory = new TransactionHistory();
        toTransactionHistory.setTransactionSeq(transactionSequence);
        toTransactionHistory.setFromAccountId(toBankAccountId);
        toTransactionHistory.setToAccountId(fromBankAccountId);
        toTransactionHistory.setMoney(money);
        toTransactionHistory.setBalance(toLastBalance + money);
        toTransactionHistory.setTransactionType(TransactionType.TRANSFER);

        return List.of(fromTransactionHistory, toTransactionHistory);
    }

    public List<TransactionResult> getTransaction(Long accountId) {
        return transactionHistoryRepository.findAllByFromAccountId(accountId).stream()
                .map(TransactionResult::of)
                .toList();
    }

    @Transactional
    public TransactionResult withdraw(Long accountId, long money) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalStateException("Account is not exists."));

        long lastBalance = bankAccount.getMoney();

        if (lastBalance < money) {
            throw new IllegalStateException("Balance is insufficient.");
        }

        TransactionHistory transactionHistory = makeTransactionHistory(accountId, -money, lastBalance, TransactionType.WITHDRAW);
        transactionHistoryRepository.save(transactionHistory);

        bankAccount.setMoney(lastBalance - money);
        bankAccountRepository.save(bankAccount);

        return TransactionResult.of(transactionHistory);
    }

    @Transactional
    public TransactionResult deposit(Long accountId, long money) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalStateException("Account is not exists."));

        long lastBalance = bankAccount.getMoney();

        TransactionHistory transactionHistory = makeTransactionHistory(accountId, money, lastBalance, TransactionType.DEPOSIT);
        transactionHistoryRepository.save(transactionHistory);

        bankAccount.setMoney(lastBalance + money);
        bankAccountRepository.save(bankAccount);

        return TransactionResult.of(transactionHistory);
    }

    private TransactionHistory makeTransactionHistory(Long accountId, long money, long lastBalance, TransactionType transactionType) {
        String transactionSequence = UUID.randomUUID().toString();

        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionSeq(transactionSequence);
        transactionHistory.setFromAccountId(accountId);
        transactionHistory.setMoney(money);
        transactionHistory.setBalance(lastBalance + money);
        transactionHistory.setTransactionType(transactionType);

        return transactionHistory;
    }
}
