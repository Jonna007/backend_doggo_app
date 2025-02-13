package com.example.backend_doggo_app.service

import com.example.backend_doggo_app.dto.PetDto
import com.example.backend_doggo_app.mapper.PetMapper
import com.example.backend_doggo_app.repository.PetRepository
import com.example.backend_doggo_app.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PetService {
    @Autowired
    lateinit var petRepository: PetRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var petMapper: PetMapper

    fun getUserPets(userId: Long): List<PetDto> {
        return petRepository.findByUserId(userId).map { petMapper.toDto(it) }
    }

    fun savePet(petDto: PetDto): PetDto {
        val user = userRepository.findById(petDto.userId).orElseThrow { EntityNotFoundException("User not found") }
        val pet = petMapper.toEntity(petDto)
        pet.user = user
        return petMapper.toDto(petRepository.save(pet))
    }

    fun updatePet(petId: Long, petDto: PetDto): PetDto {
        val existingPet = petRepository.findById(petId).orElseThrow { EntityNotFoundException("Pet not found") }
        val updatedPet = petMapper.toEntity(petDto)
        updatedPet.id = existingPet.id
        updatedPet.user = existingPet.user
        return petMapper.toDto(petRepository.save(updatedPet))
    }

    fun deletePet(petId: Long) {
        petRepository.deleteById(petId)
    }
}

