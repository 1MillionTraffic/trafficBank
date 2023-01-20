package com.trafficbank.trafficbank.dto.response;

import com.trafficbank.trafficbank.entity.Account;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AccountResponseDto {
    private Long id;
    private String number;
    private Long balance;

    public static AccountResponseDto of(Account account){
        return AccountResponseDto.builder()
                .id(account.getId())
                .number(account.getNumber())
                .balance(account.getBalance())
                .build();
    }
}
