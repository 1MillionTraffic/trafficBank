package com.trafficbank.trafficbank.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendRequestDto {
    private String fromNumber;
    private String toNumber;
    private Long money;
}
