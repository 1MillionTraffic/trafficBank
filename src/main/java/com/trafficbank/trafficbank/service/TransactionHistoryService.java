package com.trafficbank.trafficbank.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trafficbank.trafficbank.model.dto.TransactionMessage;
import com.trafficbank.trafficbank.model.dto.TransactionResult;
import com.trafficbank.trafficbank.model.enums.TransactionStatus;
import com.trafficbank.trafficbank.model.enums.TransactionType;
import com.trafficbank.trafficbank.persistence.user.entity.BankAccount;
import com.trafficbank.trafficbank.persistence.user.entity.TransactionHistory;
import com.trafficbank.trafficbank.persistence.user.repository.BankAccountRepository;
import com.trafficbank.trafficbank.persistence.user.repository.TransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionHistoryService {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final BankAccountRepository bankAccountRepository;

    @Value("${topics.transaction}")
    private String bankTopic;

    @Transactional
    public List<TransactionResult> transfer(Long fromBankAccountId, Long toBankAccountId, long money) throws JsonProcessingException {
        BankAccount fromBankAccount = bankAccountRepository.findWithPessimisticLockById(fromBankAccountId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Account is not exists."));

        // TODO: 정지 계좌인지 체크
        long fromLastBalance = fromBankAccount.getBalance();
        if (fromLastBalance < money) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Balance is insufficient.");
        }

        bankAccountRepository.findById(toBankAccountId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Account is not exists."));

        fromBankAccount.setBalance(fromLastBalance - money);

        List<TransactionHistory> transactionHistoryList = makeTransferTransactionHistoryList(fromBankAccountId, toBankAccountId, money, fromLastBalance);
        transactionHistoryRepository.saveAll(transactionHistoryList);
        bankAccountRepository.save(fromBankAccount);

        TransactionMessage transactionMessage = new TransactionMessage(fromBankAccountId, toBankAccountId, money, transactionHistoryList.get(0).getId(), transactionHistoryList.get(1).getId());

        // topic: bank-traffic, key: 받는 사람 accountId, data: 받는 사람 transaction history id (zero-payload)
        kafkaTemplate.send(bankTopic, toBankAccountId.toString(), objectMapper.writeValueAsString(transactionMessage));

        return transactionHistoryList.stream()
                .map(TransactionResult::of)
                .toList();
    }

    public List<TransactionResult> getAllTransaction() {
        return transactionHistoryRepository.findAll().stream()
                .map(TransactionResult::of)
                .toList();
    }

    private List<TransactionHistory> makeTransferTransactionHistoryList(Long fromBankAccountId, Long toBankAccountId, long money, Long fromLastBalance) {
        String transactionSequence = UUID.randomUUID().toString();

        TransactionHistory fromTransactionHistory = new TransactionHistory();
        fromTransactionHistory.setTransactionSeq(transactionSequence);
        fromTransactionHistory.setFromAccountId(fromBankAccountId);
        fromTransactionHistory.setToAccountId(toBankAccountId);
        fromTransactionHistory.setMoney(-money);
        fromTransactionHistory.setBalance(fromLastBalance - money);
        fromTransactionHistory.setTransactionType(TransactionType.TRANSFER);
        fromTransactionHistory.setTransactionStatus(TransactionStatus.PROGRESS);

        TransactionHistory toTransactionHistory = new TransactionHistory();
        toTransactionHistory.setTransactionSeq(transactionSequence);
        toTransactionHistory.setFromAccountId(toBankAccountId);
        toTransactionHistory.setToAccountId(fromBankAccountId);
        toTransactionHistory.setMoney(money);
        toTransactionHistory.setTransactionType(TransactionType.TRANSFER);
        toTransactionHistory.setTransactionStatus(TransactionStatus.PROGRESS);

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

    @Transactional
    public void completeTransaction(String message) throws JsonProcessingException {
        log.info("[completeTransaction] started. " + message);

        TransactionMessage transactionMessage = objectMapper.readValue(message, TransactionMessage.class);

        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAllByIdInAndTransactionStatus(List.of(transactionMessage.getFromTransactionId(), transactionMessage.getToTransactionId()), TransactionStatus.PROGRESS);

        Optional<BankAccount> optionalFromBankAccount = bankAccountRepository.findWithPessimisticLockById(transactionMessage.getFromBankAccountId());
        Optional<BankAccount> optionalToBankAccount = bankAccountRepository.findWithPessimisticLockById(transactionMessage.getToBankAccountId());

        if (optionalFromBankAccount.isEmpty() || optionalToBankAccount.isEmpty()) {
            transactionHistoryList.forEach(transactionHistory -> transactionHistory.setTransactionStatus(TransactionStatus.FAIL));

            transactionHistoryRepository.saveAll(transactionHistoryList);

            optionalFromBankAccount.ifPresent(account -> {
                account.setBalance(account.getBalance() + transactionMessage.getMoney());
                bankAccountRepository.save(account);
            });

            log.info("[completeTransaction] failed. from bank: " + optionalFromBankAccount.isPresent() + ", to bank: " + optionalToBankAccount.isPresent());

            return;
        }

        BankAccount toBankAccount = optionalToBankAccount.get();
        long balance = toBankAccount.getBalance() + transactionMessage.getMoney();
        log.info("[completeTransaction] toBankAccount.balance: " + toBankAccount.getBalance() + ", after balance: " + balance);
        toBankAccount.setBalance(balance);
        bankAccountRepository.save(toBankAccount);

        transactionHistoryList.get(1).setBalance(balance);
        transactionHistoryList.forEach(transactionHistory -> transactionHistory.setTransactionStatus(TransactionStatus.COMPLETED));
        transactionHistoryRepository.saveAll(transactionHistoryList);

        log.info("[completeTransaction] completed.");
    }
}
