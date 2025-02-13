package com.example.backend_doggo_app.controller

import com.example.backend_doggo_app.dto.PetDto
import com.example.backend_doggo_app.service.PetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pets")
class PetController {

    @Autowired
    lateinit var petService: PetService

    @GetMapping("/user/{userId}")
    fun getUserPets(@PathVariable userId: Long): ResponseEntity<List<PetDto>> {
        return ResponseEntity.ok(petService.getUserPets(userId))
    }

    @PostMapping
    fun savePet(@RequestBody petDto: PetDto): ResponseEntity<PetDto> {
        return ResponseEntity.ok(petService.savePet(petDto))
    }

    @PutMapping("/{petId}")
    fun updatePet(@PathVariable petId: Long, @RequestBody petDto: PetDto): ResponseEntity<PetDto> {
        return ResponseEntity.ok(petService.updatePet(petId, petDto))
    }

    @DeleteMapping("/{petId}")
    fun deletePet(@PathVariable petId: Long): ResponseEntity<Void> {
        petService.deletePet(petId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}

