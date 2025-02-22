package com.example.storeai.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeai.data.model.Product
import com.example.storeai.data.repository.ProductRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = ProductRepository()
    val products = MutableLiveData<List<Product>>()

    init {
        viewModelScope.launch {
            try {
                products.value = repository.getAllProducts()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}