package com.example.storeai.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeai.data.model.Product
import com.example.storeai.data.repository.ProductRepository
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val repository = ProductRepository()
    private val _allProducts = MutableLiveData<List<Product>>()
    private val _filteredProducts = MutableLiveData<List<Product>>()

    val filteredProducts: LiveData<List<Product>> get() = _filteredProducts

    init {
        loadAllProducts()
    }

    private fun loadAllProducts() {
        viewModelScope.launch {
            try {
                _allProducts.value = repository.getAllProducts()
                _filteredProducts.value = _allProducts.value
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun searchProducts(query: String) {
        _allProducts.value?.let { products ->
            _filteredProducts.value = products.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }
    }
}