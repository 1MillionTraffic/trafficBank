package com.trafficbank.trafficbank.controller

import com.trafficbank.trafficbank.model.dto.BaseResult
import com.trafficbank.trafficbank.service.EventService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/event")
class EventController(private val eventService: EventService) {

    @PostMapping("/request/{eventId}")
    fun requestEvent(request: HttpServletRequest, @PathVariable eventId: Long): BaseResult {
        val (result, status) = eventService.requestEvent(eventId, request.session.id)
        return BaseResult(result, status)
    }

    @GetMapping("/query/{eventId}")
    fun queryEvent(request: HttpServletRequest, @PathVariable eventId: Long): BaseResult {
        if (!eventService.checkAccess(eventId, request.session.id)) {
            return BaseResult("실패", HttpStatus.FORBIDDEN)
        }

        if (!eventService.isFinished(eventId)) {
            return BaseResult("실패", HttpStatus.NOT_ACCEPTABLE)
        }

        return BaseResult("성공")
    }

    @PostMapping("/submit/{eventId}")
    fun submitEvent(request: HttpServletRequest, @PathVariable eventId: Long): BaseResult {
        if (!eventService.submitEvent(eventId, request.session.id)) {
            return BaseResult("실패", HttpStatus.BAD_REQUEST)
        }

        return BaseResult("성공")
    }

}
