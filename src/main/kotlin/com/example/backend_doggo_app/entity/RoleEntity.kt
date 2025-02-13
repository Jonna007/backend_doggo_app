package com.example.backend_doggo_app.entity

import jakarta.persistence.*

@Entity
@Table(name = "role")
open class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    open var id: Long? = null

    @Column(nullable = false)
    open var role: String = ""

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    open lateinit var user: UserEntity
}

