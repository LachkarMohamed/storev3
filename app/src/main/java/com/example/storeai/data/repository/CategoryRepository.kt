package com.example.storeai.data.repository

import com.example.storeai.data.model.Category
import com.example.storeai.data.api.RetrofitClient

class CategoryRepository {
    private val apiService = RetrofitClient.instance

    suspend fun getAllCategories(): List<Category> {
        return apiService.getAllCategories().body() ?: emptyList()
    }
}