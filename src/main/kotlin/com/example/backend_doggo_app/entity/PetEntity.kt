package com.example.backend_doggo_app.entity

import jakarta.persistence.*

@Entity
@Table(name = "pets")
class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    lateinit var name: String

    @Column(nullable = false)
    lateinit var species: String

    var race: String? = null

    var age: Int? = null

    @Column(name = "owner_name")
    var ownerName: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    lateinit var user: UserEntity

    @OneToMany(mappedBy = "pet", cascade = [CascadeType.ALL], orphanRemoval = true)
    var vitalSigns: MutableList<VitalSignsEntity> = mutableListOf()

    @OneToMany(mappedBy = "pet", cascade = [CascadeType.ALL], orphanRemoval = true)
    var devices: MutableList<DeviceEntity> = mutableListOf()
}

