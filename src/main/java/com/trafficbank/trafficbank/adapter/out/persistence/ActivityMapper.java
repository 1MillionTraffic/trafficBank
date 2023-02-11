package com.trafficbank.trafficbank.adapter.out.persistence;

import com.trafficbank.trafficbank.domain.Activity;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {
    public Activity toActivity(ActivityEntity activityEntity) {
        return Activity.builder()
                .oneAccountId(activityEntity.getOneAccountId())
                .otherAccountId(activityEntity.getOtherAccountId())
                .money(activityEntity.getMoney())
                .createDt(activityEntity.getCreateDt())
                .build();
    }

    public ActivityEntity toActivityEntity(Activity activity){
        return ActivityEntity.builder()
                .activityId(activity.getActivityId())
                .oneAccountId(activity.getOneAccountId())
                .otherAccountId(activity.getOtherAccountId())
                .money(activity.getMoney())
                .createDt(activity.getCreateDt())
                .build();
    }
}
