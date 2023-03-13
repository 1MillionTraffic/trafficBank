package com.trafficbank.trafficbank.controller

import com.trafficbank.trafficbank.model.dto.BaseResult
import com.trafficbank.trafficbank.service.EventService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/event")
class EventController(private val eventService: EventService) {


    @PostMapping("/request/{eventId}")
    fun requestTicket(request: HttpServletRequest, @PathVariable eventId: Long): BaseResult {
        val (result, status) = eventService.requestEvent(eventId, request.session.id)
        return BaseResult(result, status)
    }

}
