package com.example.backend_doggo_app.entity

import jakarta.persistence.*

@Entity
@Table(name = "devices")
class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(unique = true)
    lateinit var deviceId: String

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    var pet: PetEntity? = null
}

