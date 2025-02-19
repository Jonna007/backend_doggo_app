package com.example.backend_doggo_app.repository

import com.example.backend_doggo_app.entity.DeviceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeviceRepository : JpaRepository<DeviceEntity, Long> {
    fun findByDeviceId(deviceId: String): DeviceEntity?
}

