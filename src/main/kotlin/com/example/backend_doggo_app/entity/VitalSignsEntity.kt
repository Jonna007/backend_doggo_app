package com.example.backend_doggo_app.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.sql.Timestamp

@Entity
@Table(name = "vital_signs")
open class VitalSignsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(updatable = false)
    open var id: Long? = null

    open var temperature: BigDecimal? = null

    @Column(name = "heart_rate")
    open var heartRate: Int? = null

    open var timestamp: Timestamp? = null

    open var device: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    open lateinit var pet: PetEntity
}

