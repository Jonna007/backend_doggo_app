package com.example.backend_doggo_app.service

import com.example.backend_doggo_app.entity.DeviceEntity
import com.example.backend_doggo_app.entity.VitalSignsEntity
import com.example.backend_doggo_app.repository.DeviceRepository
import com.example.backend_doggo_app.repository.PetRepository
import com.example.backend_doggo_app.repository.VitalSignsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class DeviceService(
    private val deviceRepository: DeviceRepository,
    private val petRepository: PetRepository,
    private val vitalSignsRepository: VitalSignsRepository
) {

    @Transactional
    fun registerDevice(deviceId: String): DeviceEntity {
        val existingDevice = deviceRepository.findByDeviceId(deviceId)
        if (existingDevice != null) {
            return existingDevice
        }
        val newDevice = DeviceEntity().apply {
            this.deviceId = deviceId
        }
        return deviceRepository.save(newDevice)
    }

    @Transactional
    fun assignDeviceToPet(deviceId: String, petId: Long) {
        val device = deviceRepository.findByDeviceId(deviceId) ?: throw IllegalArgumentException("Device not found")
        val pet = petRepository.findById(petId).orElseThrow { IllegalArgumentException("Pet not found") }
        device.pet = pet
        deviceRepository.save(device)
    }

    @Transactional
    fun recordVitalSigns(deviceId: String, temperature: BigDecimal, heartRate: Int) {
        val device = deviceRepository.findByDeviceId(deviceId) ?: throw IllegalArgumentException("Device not found")
        val pet = device.pet ?: throw IllegalArgumentException("Device is not assigned to any pet")

        val vitalSigns = VitalSignsEntity().apply {
            this.temperature = temperature
            this.heartRate = heartRate
            this.timestamp = LocalDateTime.now()
            this.pet = pet
            this.device = device
        }

        vitalSignsRepository.save(vitalSigns)
    }

    fun getLatestVitalSigns(petId: Long): VitalSignsEntity? {
        return vitalSignsRepository.findFirstByPetIdOrderByTimestampDesc(petId)
    }
}

