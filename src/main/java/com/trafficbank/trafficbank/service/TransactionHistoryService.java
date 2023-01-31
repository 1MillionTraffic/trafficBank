package com.trafficbank.trafficbank.service;

import com.trafficbank.trafficbank.model.dto.TransactionResult;
import com.trafficbank.trafficbank.model.entity.BankAccount;
import com.trafficbank.trafficbank.model.entity.TransactionHistory;
import com.trafficbank.trafficbank.model.enums.TransactionType;
import com.trafficbank.trafficbank.repository.BankAccountRepository;
import com.trafficbank.trafficbank.repository.TransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;
    private final BankAccountRepository bankAccountRepository;

    @Transactional
    public List<TransactionResult> transfer(Long fromBankAccountId, Long toBankAccountId, long money) {
        BankAccount fromBankAccount = bankAccountRepository.findWithPessimisticLockById(fromBankAccountId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Account is not exists."));

        // TODO: 정지 계좌인지 체크
        long fromLastBalance = fromBankAccount.getBalance();
        if (fromLastBalance < money) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Balance is insufficient.");
        }

        BankAccount toBankAccount = bankAccountRepository.findWithPessimisticLockById(toBankAccountId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Account is not exists."));

        long toLastBalance = toBankAccount.getBalance();

        List<TransactionHistory> transactionHistoryList = makeTransferTransactionHistoryList(fromBankAccountId, toBankAccountId, money, fromLastBalance, toLastBalance);
        transactionHistoryRepository.saveAll(transactionHistoryList);

        fromBankAccount.setBalance(fromLastBalance - money);
        toBankAccount.setBalance(toLastBalance + money);
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
        BankAccount bankAccount = bankAccountRepository.findWithPessimisticLockById(accountId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Account is not exists."));

        long lastBalance = bankAccount.getBalance();

        if (lastBalance < money) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Balance is insufficient.");
        }

        TransactionHistory transactionHistory = makeTransactionHistory(accountId, -money, lastBalance, TransactionType.WITHDRAW);
        transactionHistoryRepository.save(transactionHistory);

        bankAccount.setBalance(lastBalance - money);
        bankAccountRepository.save(bankAccount);

        return TransactionResult.of(transactionHistory);
    }

    @Transactional
    public TransactionResult deposit(Long accountId, long money) {
        BankAccount bankAccount = bankAccountRepository.findWithPessimisticLockById(accountId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Account is not exists."));

        long lastBalance = bankAccount.getBalance();

        TransactionHistory transactionHistory = makeTransactionHistory(accountId, money, lastBalance, TransactionType.DEPOSIT);
        transactionHistoryRepository.save(transactionHistory);

        bankAccount.setBalance(lastBalance + money);
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
