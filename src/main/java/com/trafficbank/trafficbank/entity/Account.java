package com.trafficbank.trafficbank.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Table(name="accounts")
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String number;

    @Column
    private Long balance;

    @Column
    private Instant createdDt;
    @Column
    private Instant modifiedDt;

    @PrePersist
    public void prePersist(){
        Instant now = Instant.now();
        this.createdDt = now;
        this.modifiedDt = now;
    }

    @PreUpdate
    public void preUpdate(){
        this.modifiedDt = Instant.now();
    }


    public void plusMoney(Long money){
        this.balance += money;
    }

    public void minusMoney(Long money){
        if(!mayMinusMoney(money)){
            throw new RuntimeException("");
        }
        this.balance -= money;
    }

    private boolean mayMinusMoney(Long money){
        return this.balance >= money;
    }
}
