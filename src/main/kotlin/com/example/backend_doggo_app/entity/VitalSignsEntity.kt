package com.example.backend_doggo_app.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "vital_signs")
class VitalSignsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var temperature: BigDecimal? = null

    @Column(name = "heart_rate")
    var heartRate: Int? = null

    var timestamp: LocalDateTime = LocalDateTime.now()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    lateinit var pet: PetEntity

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    lateinit var device: DeviceEntity
}

