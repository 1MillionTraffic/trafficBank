package com.trafficbank.trafficbank.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Table(name="activity")
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ActivityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDateTime createdDate;
    @Column
    private Long ownerAccountId;
    @Column
    private Long sourceAccountId;
    @Column
    private Long targetAccountId;
    @Column
    private Long amount;
}
