package com.example.backend_doggo_app.mapper

import com.example.backend_doggo_app.dto.PetDto
import com.example.backend_doggo_app.entity.PetEntity
import org.springframework.stereotype.Component

@Component
class PetMapper {
    fun toDto(entity: PetEntity): PetDto {
        return PetDto(
            id = entity.id,
            name = entity.name ?: "",
            species = entity.species ?: "",
            race = entity.race,
            age = entity.age,
            ownerName = entity.ownerName,
            userId = entity.user?.id ?: 0
        )
    }

    fun toEntity(dto: PetDto): PetEntity {
        val entity = PetEntity()
        entity.id = dto.id
        entity.name = dto.name
        entity.species = dto.species
        entity.race = dto.race
        entity.age = dto.age
        entity.ownerName = dto.ownerName
        return entity
    }
}

