package com.example.storeai.utils

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.storeai.data.model.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object CartManager {
    private const val CART_PREF = "cart_pref"
    private const val CART_ITEMS_KEY = "cart_items"
    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> get() = _cartItems
    private val _totalPrice = MutableLiveData<Double>(0.0)
    val totalPrice: LiveData<Double> get() = _totalPrice

    data class CartItem(
        val productId: String,
        val title: String,
        val price: Double,
        val image: String,
        var quantity: Int = 1
    )

    fun addToCart(context: Context, product: Product) {
        val prefs = context.getSharedPreferences(CART_PREF, Context.MODE_PRIVATE)
        val items = getCartItems(context).toMutableList()

        val existingItem = items.find { it.productId == product.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            items.add(CartItem(
                productId = product.id,
                title = product.title,
                price = product.price,
                image = product.image
            ))
        }

        saveCartItems(context, items)
        updateLiveData(items)
    }

    fun removeFromCart(context: Context, productId: String) {
        val items = getCartItems(context).toMutableList()
        items.removeAll { it.productId == productId }
        saveCartItems(context, items)
        updateLiveData(items)
    }

    fun updateQuantity(context: Context, productId: String, newQuantity: Int) {
        val items = getCartItems(context).toMutableList()
        items.find { it.productId == productId }?.quantity = newQuantity
        saveCartItems(context, items)
        updateLiveData(items)
    }

    private fun saveCartItems(context: Context, items: List<CartItem>) {
        val json = Gson().toJson(items)
        context.getSharedPreferences(CART_PREF, Context.MODE_PRIVATE)
            .edit()
            .putString(CART_ITEMS_KEY, json)
            .apply()
    }

    fun getCartItems(context: Context): List<CartItem> {
        val json = context.getSharedPreferences(CART_PREF, Context.MODE_PRIVATE)
            .getString(CART_ITEMS_KEY, null) ?: return emptyList()

        val type = object : TypeToken<List<CartItem>>() {}.type
        return Gson().fromJson(json, type) ?: emptyList()
    }

    private fun updateLiveData(items: List<CartItem>) {
        _cartItems.value = items
        _totalPrice.value = items.sumOf { it.price * it.quantity } ?: 0.0
    }
}