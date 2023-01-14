package com.trafficbank.trafficbank.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class ActivityWindow {
    private List<Activity> activities;

    public ActivityWindow(List<Activity> activities){
        this.activities = activities;
    }


    public void addActivity(Activity activity){
        this.activities.add(activity);
    }

    public Money calculateBalance(Long accountId){
        Money depositBalance = this.activities.stream()
                .filter(activity -> activity.getTargetAccountId().equals(accountId))
                .map(Activity::getMoney)
                .reduce(Money.ZERO, Money::add);
        Money withdrawBalance = this.activities.stream()
                .filter(activity -> activity.getSourceAccountId().equals(accountId))
                .map(Activity::getMoney)
                .reduce(Money.ZERO, Money::add);
        return Money.subtract(depositBalance, withdrawBalance);
    }


}
