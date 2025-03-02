package com.example.storeai.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeai.data.model.Product
import com.example.storeai.data.repository.ProductRepository
import kotlinx.coroutines.launch
import com.example.storeai.data.model.Category
import com.example.storeai.data.repository.CategoryRepository

class HomeViewModel : ViewModel() {
    private val productRepo = ProductRepository()
    private val categoryRepo = CategoryRepository()
    val products = MutableLiveData<List<Product>>()
    val categories = MutableLiveData<List<Category>>()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                products.value = productRepo.getAllProducts()
                categories.value = categoryRepo.getAllCategories()
            } catch (e: Exception) {
                // Handle errors
            }
        }
    }
}