package com.trafficbank.trafficbank.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Getter
@Table(name="accounts")
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private String accountNumber;
    private Long balance;
    private Instant createdDt;
    private Instant modifiedDt;



}
