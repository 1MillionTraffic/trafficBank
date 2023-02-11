package com.trafficbank.trafficbank.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class Activity {
    private Long activityId;
    private Long oneAccountId;
    private Long otherAccountId;
    private Long money;

    private ActivityType activityType;
    private Instant createDt;

    @Builder
    public Activity(Long activityId, Long oneAccountId, Long otherAccountId, Long money, ActivityType activityType, Instant createDt) {
        this.activityId = activityId;
        this.oneAccountId = oneAccountId;
        this.otherAccountId = otherAccountId;
        this.money = money;
        this.activityType = activityType;
        this.createDt = createDt;
    }
}
