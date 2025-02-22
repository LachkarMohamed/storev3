package com.example.storeai.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeai.data.model.Product
import com.example.storeai.data.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val repository = ProductRepository()
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    fun loadProductDetails(productId: String) {
        viewModelScope.launch {
            try {
                _product.value = repository.getProductById(productId)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}