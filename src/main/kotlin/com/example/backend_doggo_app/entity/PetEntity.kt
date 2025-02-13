package com.example.backend_doggo_app.entity

import jakarta.persistence.*

@Entity
@Table(name = "pets")
open class PetEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(updatable = false)
    open var id: Long? = null

    @Column(nullable = false)
    open var name: String = ""

    @Column(nullable = false)
    open var species: String = ""

    open var race: String? = null

    open var age: Int? = null

    @Column(name = "owner_name")
    open var ownerName: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    open var user: UserEntity? = null

    @OneToMany(mappedBy = "pet", cascade = [CascadeType.ALL], orphanRemoval = true)
    open var vitalSigns: MutableList<VitalSignsEntity> = mutableListOf()
}

