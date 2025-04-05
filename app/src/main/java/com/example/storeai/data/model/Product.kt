package com.example.storeai.data.model

data class Product(
    val id: String,
    val title: String,
    val price: Double,
    val description: String,
    val image: String,
    val categorie_id: String,
    val category_title: String,
    val similar_products_ids: List<String>? = null,

)

/*data class Product(
    val id: String,
    val title: String,
    val price: Double,
    val description: String?,
    val image: String,
    val category_title: String,
    val similar_products_ids: List<String>? = null,
    val offerPercentage: Float? = null,
    val colors: List<Int>? = null,
    val sizes: List<String>? = null,
    val images: List<String>? = null
)*/
/*
[
  {
    "categorie_id": "21",
    "category_title": "shoes"
  },
  {
    "categorie_id": "23",
    "category_title": "glasses"
  }
 ]
* */