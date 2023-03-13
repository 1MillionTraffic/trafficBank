package com.trafficbank.trafficbank.repository

import com.trafficbank.trafficbank.model.entity.Event
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface EventRepository : JpaRepository<Event, Long> {
    @Transactional
    @Modifying
    @Query("update Event e set e.isFullLimit = :isFullLimit")
    fun updateIsFullLimit(isFullLimit: Boolean)
}
