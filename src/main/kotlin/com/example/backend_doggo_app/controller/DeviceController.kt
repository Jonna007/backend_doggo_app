package com.example.backend_doggo_app.controller

import com.example.backend_doggo_app.service.DeviceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/device")
class DeviceController(private val deviceService: DeviceService) {

    @PostMapping("/register")
    fun registerDevice(@RequestParam deviceId: String): ResponseEntity<String> {
        deviceService.registerDevice(deviceId)
        return ResponseEntity.ok("Device registered successfully")
    }

    @PostMapping("/assign")
    fun assignDevice(@RequestParam deviceId: String, @RequestParam petId: Long): ResponseEntity<String> {
        deviceService.assignDeviceToPet(deviceId, petId)
        return ResponseEntity.ok("Device assigned to pet successfully")
    }

    @PostMapping("/record")
    fun recordVitalSigns(
        @RequestParam deviceId: String,
        @RequestParam temperature: BigDecimal,
        @RequestParam heartRate: Int
    ): ResponseEntity<String> {
        deviceService.recordVitalSigns(deviceId, temperature, heartRate)
        return ResponseEntity.ok("Vital signs recorded successfully")
    }

    @GetMapping("/vitals/{petId}")
    fun getLatestVitalSigns(@PathVariable petId: Long): ResponseEntity<Any> {
        val vitalSigns = deviceService.getLatestVitalSigns(petId)
        return if (vitalSigns != null) {
            ResponseEntity.ok(mapOf(
                "temperature" to vitalSigns.temperature,
                "heartRate" to vitalSigns.heartRate,
                "timestamp" to vitalSigns.timestamp
            ))
        } else {
            ResponseEntity.notFound().build()
        }
    }
}

