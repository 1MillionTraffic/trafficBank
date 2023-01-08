package com.trafficbank.trafficbank.model.entity;

import com.trafficbank.trafficbank.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionSeq;
    private Long money;
    private Long balance;
    private Long fromAccountId;
    private Long toAccountId;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @CreationTimestamp
    private Instant createdDt;
}
