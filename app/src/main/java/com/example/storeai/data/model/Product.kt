package com.example.storeai.data.model

data class Product(
    val id: String,
    val title: String,
    val price: Double,
    val description: String,
    val image: String,
    val category_title: String,
    val similar_products_ids: List<String>? = null
)