package com.trafficbank.trafficbank.model.dto

import com.trafficbank.trafficbank.persistence.bank.entity.Event

data class EventDTO(
    val id: Long,
    val requestLimitUser: Long,
    val isFullRequestLimit: Boolean
) {
    constructor(event: Event) : this(event.id!!, event.requestLimitUser, event.isFullRequestLimit)
}
