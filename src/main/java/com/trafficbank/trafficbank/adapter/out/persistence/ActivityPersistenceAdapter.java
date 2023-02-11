package com.trafficbank.trafficbank.adapter.out.persistence;

import com.trafficbank.trafficbank.application.outPort.SaveActivityPort;
import com.trafficbank.trafficbank.domain.Activity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActivityPersistenceAdapter implements SaveActivityPort {
    private ActivityMapper activityMapper;
    private ActivityJpaRepository activityRepository;

    @Override
    public void saveActivity(Activity activity) {
        ActivityEntity activityEntity = activityMapper.toActivityEntity(activity);
        activityRepository.save(activityEntity);
    }
}
