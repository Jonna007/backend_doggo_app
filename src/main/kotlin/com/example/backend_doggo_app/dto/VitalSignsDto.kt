package com.example.backend_doggo_app.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class VitalSignsDto(
    val id: Long?,
    val temperature: BigDecimal?,
    val heartRate: Int?,
    val timestamp: LocalDateTime,
    val device: String?,
    val petId: Long
)

