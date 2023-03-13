package com.trafficbank.trafficbank.model.dto

import com.trafficbank.trafficbank.model.entity.Event

data class EventDTO(
    val id: Long,
    val limitUser: Long,
    val isFullLimit: Boolean
) {
    constructor(event: Event) : this(event.id!!, event.limitUser, event.isFullLimit)
}
