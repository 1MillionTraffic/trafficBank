package com.trafficbank.trafficbank.service;


import com.trafficbank.trafficbank.dto.response.ActivityResponseDto;
import com.trafficbank.trafficbank.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetActivitiesService {
    private final ActivityRepository activityRepository;

    @Transactional(readOnly = true)
    public List<ActivityResponseDto> findAll(String accountNumber){
        return activityRepository.findAllByAccountNumber(accountNumber).stream()
                .map(ActivityResponseDto::of).collect(Collectors.toList());
    }
}
