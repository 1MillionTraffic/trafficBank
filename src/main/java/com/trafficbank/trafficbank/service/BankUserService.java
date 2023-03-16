package com.trafficbank.trafficbank.service;

import com.trafficbank.trafficbank.persistence.user.entity.BankUser;
import com.trafficbank.trafficbank.persistence.user.repository.BankUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankUserService {

    private final BankUserRepository bankUserRepository;

    public List<BankUser> getAllBankUser() {
        return bankUserRepository.findAll();
    }

    public BankUser getBankUser(Long userId) {
        return bankUserRepository.findById(userId)
                .orElse(null);
    }

    public BankUser createBankUser(String name) {
        BankUser bankUser = new BankUser();
        bankUser.setSeq(UUID.randomUUID().toString());
        bankUser.setName(name);

        bankUserRepository.save(bankUser);

        return bankUser;
    }

    public BankUser updateBankUser(Long userId, String name) {
        BankUser bankUser = bankUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User is not exist."));

        bankUser.setName(name);
        bankUserRepository.save(bankUser);

        return bankUser;
    }

    public void deleteBankUser(Long userId) {
        BankUser bankUser = bankUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User is not exist."));

        bankUserRepository.delete(bankUser);
    }

}
