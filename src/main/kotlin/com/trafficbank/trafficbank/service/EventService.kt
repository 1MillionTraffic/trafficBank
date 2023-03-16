package com.trafficbank.trafficbank.service

import com.trafficbank.trafficbank.model.dto.EventDTO
import com.trafficbank.trafficbank.persistence.bank.repository.EventRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
            eventRepository.updateIsFullRequestLimit(true)
            redisService.clear(eventKey.format(eventId))
        }

        if (userCount > eventDTO.requestLimitUser) {
            return false
        }

        return true
    }

    fun getEventDTO(eventId: Long): EventDTO? {
        val cacheKey = eventKey.format(eventId)
        val eventDTO = redisService.get(cacheKey, EventDTO::class.java)
        if (eventDTO != null) return eventDTO

        val event = eventRepository.findById(eventId).getOrNull() ?: return null

        redisService.set(cacheKey, event, Duration.ofMinutes(30))

        return EventDTO(event)
    }

    fun checkAccess(eventId: Long, sessionId: String): Boolean {
        return redisService.isMemberSet(eventUserSetKey.format(eventId), sessionId)
    }

    fun isFinished(eventId: Long): Boolean {
        return eventRepository.findById(eventId).getOrNull()
            ?.let { it.limitUser > it.currentUserCnt }
            ?: return false
    }

    @Transactional
    fun submitEvent(eventId: Long, sessionId: String): Boolean {
        if (!checkAccess(eventId, sessionId)) {
            return false
        }

        val event = eventRepository.findWithPessimisticLockById(eventId) ?: return false
        if (event.limitUser <= event.currentUserCnt) {
            return false
        }

        eventRepository.save(event.copy(currentUserCnt = event.currentUserCnt + 1))
        // 그 외 기타 비즈니스 로직 저장

        return true
    }

}
