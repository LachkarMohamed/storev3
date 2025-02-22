package com.example.storeai.data.model

data class RegisterResponse(
    val user: User,
    val token: String
)

data class User(
    val id: String,
    val username: String,
    val email: String
)