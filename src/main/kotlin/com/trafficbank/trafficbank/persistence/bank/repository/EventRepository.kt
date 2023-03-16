package com.trafficbank.trafficbank.persistence.bank.repository

import com.trafficbank.trafficbank.persistence.bank.entity.Event
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface EventRepository : JpaRepository<Event, Long> {
    @Transactional
    @Modifying
    @Query("update Event e set e.isFullRequestLimit = :isFullRequestLimit")
    fun updateIsFullRequestLimit(isFullRequestLimit: Boolean)

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findWithPessimisticLockById(eventId: Long): Event?
}
