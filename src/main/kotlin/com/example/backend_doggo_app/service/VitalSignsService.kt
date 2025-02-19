package com.example.backend_doggo_app.service

import com.example.backend_doggo_app.dto.VitalSignsDto
import com.example.backend_doggo_app.mapper.VitalSignsMapper
import com.example.backend_doggo_app.repository.DeviceRepository
import com.example.backend_doggo_app.repository.PetRepository
import com.example.backend_doggo_app.repository.VitalSignsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class VitalSignsService(
    private val vitalSignsRepository: VitalSignsRepository,
    private val petRepository: PetRepository,
    private val deviceRepository: DeviceRepository,
    private val vitalSignsMapper: VitalSignsMapper
) {

    fun getPetVitalSigns(petId: Long): List<VitalSignsDto> {
        return vitalSignsRepository.findByPetId(petId).map { vitalSignsMapper.toDto(it) }
    }

    @Transactional
    fun saveVitalSigns(vitalSignsDto: VitalSignsDto): VitalSignsDto {
        val pet = petRepository.findById(vitalSignsDto.petId).orElseThrow { IllegalArgumentException("Pet not found") }
        val device = deviceRepository.findByDeviceId(vitalSignsDto.device ?: "") ?: throw IllegalArgumentException("Device not found")
        val vitalSigns = vitalSignsMapper.toEntity(vitalSignsDto)
        vitalSigns.pet = pet
        vitalSigns.device = device
        return vitalSignsMapper.toDto(vitalSignsRepository.save(vitalSigns))
    }
}

