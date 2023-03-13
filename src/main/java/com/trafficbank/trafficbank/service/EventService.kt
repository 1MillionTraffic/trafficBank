package com.trafficbank.trafficbank.service

import com.trafficbank.trafficbank.model.dto.EventDTO
import com.trafficbank.trafficbank.repository.EventRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.Duration
import kotlin.jvm.optionals.getOrNull

@Service
class EventService(
    private val redisService: RedisService,
    private val eventRepository: EventRepository
) {
    companion object {
        private const val eventKey = "Event:%d"
        private const val eventLimitCountKey = "EventLimit:%d"
        private const val eventUserSetKey = "EventUserSet:%d"
    }

    fun requestEvent(eventId: Long, sessionId: String): Pair<String?, HttpStatus> {
        val eventDTO = getEventDTO(eventId) ?: return null to HttpStatus.BAD_REQUEST

        if (eventDTO.isFullRequestLimit || !checkValidUser(eventDTO)) {
            return "실패" to HttpStatus.FORBIDDEN
        }

        redisService.addSet(eventUserSetKey.format(eventId), sessionId)

        return "성공" to HttpStatus.OK
    }

    private fun checkValidUser(eventDTO: EventDTO): Boolean {
        val eventId = eventDTO.id
        val userCount = redisService.increment(eventLimitCountKey.format(eventId))

        if (userCount == eventDTO.requestLimitUser) {
            eventRepository.updateIsFullLimit(true)
            redisService.clear(eventKey.format(eventId))
        }

        if (userCount > eventDTO.requestLimitUser) {
            return false
        }

        return true
    }

    private fun getEventDTO(eventId: Long): EventDTO? {
        val cacheKey = eventKey.format(eventId)
        redisService.get(cacheKey, EventDTO::class.java) ?: return null

        val event = eventRepository.findById(eventId).getOrNull() ?: return null

        redisService.set(cacheKey, event, Duration.ofMinutes(30))

        return EventDTO(event)
    }

    fun checkAccess(eventId: Long, sessionId: String): Boolean {
        return redisService.isMemberSet(eventUserSetKey.format(eventId), sessionId)
    }

    fun isFinished(eventId: Long): Boolean {
        return eventRepository.findWithPessimisticLockById(eventId)
            ?.let { it.limitUser > it.currentUser }
            ?: return false
    }

}
