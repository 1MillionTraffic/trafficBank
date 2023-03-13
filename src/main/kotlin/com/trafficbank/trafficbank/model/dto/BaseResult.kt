package com.trafficbank.trafficbank.model.dto

import org.springframework.http.HttpStatus

data class BaseResult(
    val result: Any? = null,
    val status: HttpStatus = HttpStatus.OK
)
