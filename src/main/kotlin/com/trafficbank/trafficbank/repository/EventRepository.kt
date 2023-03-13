package com.trafficbank.trafficbank.repository

import com.trafficbank.trafficbank.model.entity.Event
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
    fun updateIsFullLimit(isFullRequestLimit: Boolean)

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findWithPessimisticLockById(eventId: Long): Event?
}
