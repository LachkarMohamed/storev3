package com.example.storeai.data.repository

import com.example.storeai.data.api.ApiService
import com.example.storeai.data.api.RetrofitClient
import com.example.storeai.data.model.RegisterRequest
import com.example.storeai.data.model.RegisterResponse
import com.example.storeai.data.model.LoginRequest
import com.example.storeai.data.model.LoginResponse

class UserRepository {
    private val apiService = RetrofitClient.instance

    suspend fun registerUser(request: RegisterRequest): RegisterResponse {
        val response = apiService.registerUser(request)
        if (!response.isSuccessful) {
            throw Exception("Registration failed: ${response.code()}")
        }
        return response.body() ?: throw Exception("Empty response body")

    }

    suspend fun loginUser(request: LoginRequest): LoginResponse {
        val response = apiService.loginUser(request)
        if (!response.isSuccessful) {
            throw Exception("Login failed: ${response.code()}")
        }
        return response.body() ?: throw Exception("Empty response body")
    }
}