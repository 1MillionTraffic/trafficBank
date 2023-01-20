package com.trafficbank.trafficbank.dto.request;

import com.trafficbank.trafficbank.entity.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateAccountRequestDto {
    private String number;

    public Account toEntity(){
        return Account.builder()
                .balance(0L)
                .number(number)
                .build();
    }
}
