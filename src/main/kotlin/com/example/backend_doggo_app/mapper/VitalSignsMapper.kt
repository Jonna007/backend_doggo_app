package com.example.backend_doggo_app.mapper

import com.example.backend_doggo_app.dto.VitalSignsDto
import com.example.backend_doggo_app.entity.VitalSignsEntity
import org.springframework.stereotype.Component
import java.sql.Timestamp

@Component
class VitalSignsMapper {
    fun toDto(entity: VitalSignsEntity): VitalSignsDto {
        return VitalSignsDto(
            id = entity.id,
            temperature = entity.temperature,
            heartRate = entity.heartRate,
            timestamp = entity.timestamp?.toLocalDateTime() ?: java.time.LocalDateTime.now(),
            device = entity.device,
            petId = entity.pet?.id ?: 0
        )
    }

    fun toEntity(dto: VitalSignsDto): VitalSignsEntity {
        val entity = VitalSignsEntity()
        entity.id = dto.id
        entity.temperature = dto.temperature
        entity.heartRate = dto.heartRate
        entity.timestamp = Timestamp.valueOf(dto.timestamp)
        entity.device = dto.device
        return entity
    }
}

