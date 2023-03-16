package com.trafficbank.trafficbank.persistence.bank.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
data class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val requestLimitUser: Long,
    val isFullRequestLimit: Boolean,
    val limitUser: Long,
    val currentUserCnt: Long,
    @CreationTimestamp
    val createdDt: Instant? = null,
    @UpdateTimestamp
    val updatedDt: Instant? = null,
)
