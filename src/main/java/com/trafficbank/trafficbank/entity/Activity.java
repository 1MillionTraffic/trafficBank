package com.trafficbank.trafficbank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Table(name="activities")
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="account_number")
    private String accountNumber;
    @Column(name = "other_account_number")
    private String otherAccountNumber;
    @Column
    private Long amount;
    @Column
    private Long balance;
    @Column
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    @Column
    private Instant createdDt;

    @PrePersist
    public void prePersist(){
        this.createdDt = Instant.now();
    }

}
