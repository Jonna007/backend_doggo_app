package com.example.backend_doggo_app.entity

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "users")
open class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    open var id: Long? = null

    @Column(name = "name")
    open var name: String = ""

    @Column(name = "email", unique = true)
    open var email: String = ""

    @Column(nullable = false)
    open var password: String = ""

    @Column(name = "registration_date")
    open var registrationDate: Timestamp? = null

    @Column(nullable = false)
    open var locked: Boolean = false

    @Column(nullable = false)
    open var disabled: Boolean = false

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    open var roles: MutableSet<RoleEntity> = mutableSetOf()
}

