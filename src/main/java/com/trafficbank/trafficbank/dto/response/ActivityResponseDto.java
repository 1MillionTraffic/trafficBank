package com.trafficbank.trafficbank.dto.response;

import com.trafficbank.trafficbank.entity.Activity;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ActivityResponseDto {
    private Long id;
    private String otherAccountNumber;
    private Long amount;
    private Long balance;
    private String activityType;

    public static ActivityResponseDto of(Activity activity){
        return ActivityResponseDto.builder()
                .id(activity.getId())
                .amount(activity.getAmount())
                .otherAccountNumber(activity.getOtherAccountNumber())
                .balance(activity.getBalance())
                .activityType(activity.getActivityType().toString())
                .build();
    }
}
