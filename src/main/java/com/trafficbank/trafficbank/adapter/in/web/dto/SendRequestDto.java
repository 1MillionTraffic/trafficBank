package com.trafficbank.trafficbank.adapter.in.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendRequestDto {
    private String sourceAccountNumber;
    private String targetAccountNumber;
    private Long money;
}
