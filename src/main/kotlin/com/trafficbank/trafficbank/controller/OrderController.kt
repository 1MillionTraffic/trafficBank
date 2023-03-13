package com.trafficbank.trafficbank.controller

import com.trafficbank.trafficbank.model.dto.BaseResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/order")
class OrderController {

    @PostMapping("/sync")
    fun syncOrder(): BaseResult {
        // 주문 저장
        // 결제 처리
        // 이상 결제 체크
        // push/email
        // 계정 update
        // 장바구니 update
        return BaseResult()
    }

    @PostMapping("/async")
    fun asyncOrder(): BaseResult {
        // 주문 저장 (TempOrder, EDAGateway - Kafka message send)
        return BaseResult()
    }

    fun orderListener() {
        // kafka-consumer1 - 결제 처리(재고 확인, 잔고 확인) -> 성공시 callback 호출, 실패 재처리 큐

        // callback에서 kafka message 3개 전송 (이상 결제 체크, push/email, 계정/장바구니 update)
        // kafka-consumer2 - 이상 결제 체크
        // kafka-consumer3 - push/email 발송
        // kafka-consumer4 - 계정, 장바구니 update
    }

    fun queryOrder() {
        // kafka streams aggregation을 이용해서 조회
    }


}
