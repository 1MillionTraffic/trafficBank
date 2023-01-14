package com.trafficbank.trafficbank.adapter;

import com.trafficbank.trafficbank.domain.Account;
import com.trafficbank.trafficbank.domain.Activity;
import com.trafficbank.trafficbank.entity.AccountEntity;
import com.trafficbank.trafficbank.entity.ActivityEntity;
import com.trafficbank.trafficbank.repository.AccountJpaRepository;
import com.trafficbank.trafficbank.repository.ActivityJpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public class AccountPersistenceAdapter {
    private final AccountJpaRepository accountRepository;
    private final ActivityJpaRepository activityRepository;

    private final Mapper mapper;

    public AccountPersistenceAdapter(AccountJpaRepository accoutnRepository, ActivityJpaRepository activityRepository, Mapper mapper) {
        this.accountRepository = accoutnRepository;
        this.activityRepository = activityRepository;
        this.mapper = mapper;
    }

    public Account getAccount(Long accountId, LocalDateTime baselineDate) {
        AccountEntity accountEntity = accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException(""));
        List<ActivityEntity> activityEntities = activityRepository.findByOwnerIdAfterCreatedDate(accountId, baselineDate);
        Long withdrawBalance = orZero(activityRepository.getWithdrawBalanceBeforeCreatedDate(accountId, baselineDate));
        Long depositBalance = orZero(activityRepository.getDepositBalanceBeforeCreatedDate(accountId, baselineDate));
        return mapper.mapToAccount(accountEntity, activityEntities, withdrawBalance, depositBalance);
    }

    public void saveActivities(Account account) {
        for (Activity activity : account.getActivityWindow().getActivities()) {
            if (activity.getId() == null) {
                activityRepository.save(mapper.mapToActivityEntity(activity));
            }
        }
    }

    private Long orZero(Long value) {
        return value == null ? 0L : value;
    }
}
