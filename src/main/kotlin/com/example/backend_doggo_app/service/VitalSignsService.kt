package com.example.backend_doggo_app.service

import com.example.backend_doggo_app.dto.VitalSignsDto
import com.example.backend_doggo_app.mapper.VitalSignsMapper
import com.example.backend_doggo_app.repository.PetRepository
import com.example.backend_doggo_app.repository.VitalSignsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VitalSignsService {
    @Autowired
    lateinit var vitalSignsRepository: VitalSignsRepository

    @Autowired
    lateinit var petRepository: PetRepository

    @Autowired
    lateinit var vitalSignsMapper: VitalSignsMapper

    fun getPetVitalSigns(petId: Long): List<VitalSignsDto> {
        return vitalSignsRepository.findByPetId(petId).map { vitalSignsMapper.toDto(it) }
    }

    fun saveVitalSigns(vitalSignsDto: VitalSignsDto): VitalSignsDto {
        val pet = petRepository.findById(vitalSignsDto.petId).orElseThrow()
        val vitalSigns = vitalSignsMapper.toEntity(vitalSignsDto)
        vitalSigns.pet = pet
        return vitalSignsMapper.toDto(vitalSignsRepository.save(vitalSigns))
    }
}

