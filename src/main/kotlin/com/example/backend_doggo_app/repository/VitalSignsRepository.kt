package com.example.backend_doggo_app.repository

import com.example.backend_doggo_app.entity.VitalSignsEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VitalSignsRepository : JpaRepository<VitalSignsEntity, Long> {
    fun findByPetId(petId: Long): List<VitalSignsEntity>
}

