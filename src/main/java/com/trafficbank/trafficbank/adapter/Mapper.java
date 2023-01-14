package com.trafficbank.trafficbank.adapter;

import com.trafficbank.trafficbank.domain.Account;
import com.trafficbank.trafficbank.domain.Activity;
import com.trafficbank.trafficbank.domain.ActivityWindow;
import com.trafficbank.trafficbank.domain.Money;
import com.trafficbank.trafficbank.entity.AccountEntity;
import com.trafficbank.trafficbank.entity.ActivityEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {
    // domain -> entity
    ActivityEntity mapToActivityEntity(Activity activity) {
        return ActivityEntity.builder()
                .id(activity.getId() == null ? null : activity.getId())
                .ownerAccountId(activity.getOwnerAccountId())
                .sourceAccountId(activity.getSourceAccountId())
                .targetAccountId(activity.getTargetAccountId())
                .amount(activity.getMoney().getAmount().longValue())
                .createdDate(activity.getCreatedDate())
                .build();
    }

    // entity -> domain
    ActivityWindow mapToActivityWindow(List<ActivityEntity> activityEntities){
        List<Activity> activities = activityEntities.stream().map(activityEntity -> {
            return Activity.builder()
                    .id(activityEntity.getId())
                    .ownerAccountId(activityEntity.getOwnerAccountId())
                    .sourceAccountId(activityEntity.getSourceAccountId())
                    .targetAccountId(activityEntity.getTargetAccountId())
                    .createdDate(activityEntity.getCreatedDate())
                    .money(Money.of(activityEntity.getAmount()))
                    .build();
        }).collect(Collectors.toList());
        return new ActivityWindow(activities);
    }

    Account mapToAccount(AccountEntity accountEntity, List<ActivityEntity> activityEntities, Long withdrawBalance, Long depositBalance){
        Money baselineBalance = Money.subtract(Money.of(depositBalance), Money.of(withdrawBalance));
        return Account.builder()
                .id(accountEntity.getId())
                .baseBalance(baselineBalance)
                .activityWindow(mapToActivityWindow(activityEntities))
                .build();
    }
}
