package com.trafficbank.trafficbank.entity;


import jakarta.persistence.*;
import lombok.*;


@Getter
@Table(name="account")
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;





}
