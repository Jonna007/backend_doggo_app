package com.example.backend_doggo_app.dto

data class PetDto(
    val id: Long?,
    val name: String,
    val species: String,
    val race: String?,
    val age: Int?,
    val ownerName: String?,
    val userId: Long
)

