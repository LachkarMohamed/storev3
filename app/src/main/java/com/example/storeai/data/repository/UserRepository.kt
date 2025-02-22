package com.example.storeai.data.repository

import com.example.storeai.data.api.ApiService
import com.example.storeai.data.api.RetrofitClient
import com.example.storeai.data.model.RegisterRequest
import com.example.storeai.data.model.RegisterResponse

class UserRepository {
    private val apiService = RetrofitClient.instance

    suspend fun registerUser(request: RegisterRequest): RegisterResponse {
        val response = apiService.registerUser(request)
        if (!response.isSuccessful) {
            throw Exception("Registration failed: ${response.code()}")
        }
        return response.body() ?: throw Exception("Empty response body")
    }
}