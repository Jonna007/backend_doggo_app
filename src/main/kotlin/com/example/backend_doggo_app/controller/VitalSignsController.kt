package com.example.backend_doggo_app.controller

import com.example.backend_doggo_app.dto.VitalSignsDto
import com.example.backend_doggo_app.service.VitalSignsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/vital-signs")
class VitalSignsController {

    @Autowired
    lateinit var vitalSignsService: VitalSignsService

    @GetMapping("/pet/{petId}")
    fun getPetVitalSigns(@PathVariable petId: Long): ResponseEntity<List<VitalSignsDto>> {
        return ResponseEntity.ok(vitalSignsService.getPetVitalSigns(petId))
    }

    @PostMapping
    fun saveVitalSigns(@RequestBody vitalSignsDto: VitalSignsDto): ResponseEntity<VitalSignsDto> {
        return ResponseEntity.ok(vitalSignsService.saveVitalSigns(vitalSignsDto))
    }
}

