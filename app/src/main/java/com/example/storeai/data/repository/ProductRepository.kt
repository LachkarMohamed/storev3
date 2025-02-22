package com.example.storeai.data.repository

import com.example.storeai.data.api.ApiService
import com.example.storeai.data.api.RetrofitClient
import com.example.storeai.data.model.Product


class ProductRepository {
    private val apiService = RetrofitClient.instance

    suspend fun getAllProducts(): List<Product> {
        return apiService.getAllProducts().body() ?: emptyList()
    }

    suspend fun getProductById(id: String): Product {
        return apiService.getProductById(id).body() ?: throw Exception("Product not found")
    }
}