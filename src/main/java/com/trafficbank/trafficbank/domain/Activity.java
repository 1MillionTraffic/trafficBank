package com.trafficbank.trafficbank.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Activity {
    private Long id;
    private Long ownerAccountId;
    private Long sourceAccountId;
    private Long targetAccountId;
    private Money money;
    private LocalDateTime createdDate;
}
