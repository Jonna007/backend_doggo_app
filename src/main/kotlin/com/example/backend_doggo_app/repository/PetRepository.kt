package com.example.backend_doggo_app.repository

import com.example.backend_doggo_app.entity.PetEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PetRepository : JpaRepository<PetEntity, Long> {
    fun findByUserId(userId: Long): List<PetEntity>
}

