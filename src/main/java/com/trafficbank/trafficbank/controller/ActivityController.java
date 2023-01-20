package com.trafficbank.trafficbank.controller;

import com.trafficbank.trafficbank.dto.response.ActivityResponseDto;
import com.trafficbank.trafficbank.service.GetActivitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/accounts")
@RestController
@RequiredArgsConstructor
public class ActivityController {
    private final GetActivitiesService getActivitiesService;

    @GetMapping("/{accountNumber}")
    public List<ActivityResponseDto> findAl(@PathVariable String accountNumber){
        return getActivitiesService.findAll(accountNumber);
    }
}
