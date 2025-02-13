package com.example.backend_doggo_app.dto

data class UpdateUserDto(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null
)

