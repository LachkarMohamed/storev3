package com.example.storeai.ui.cart

import androidx.lifecycle.ViewModel
import com.example.storeai.utils.CartManager

class CartViewModel : ViewModel() {
    val cartItems = CartManager.cartItems
    val totalPrice = CartManager.totalPrice
}