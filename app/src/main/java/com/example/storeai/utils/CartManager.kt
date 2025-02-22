package com.example.storeai.utils

import android.content.Context


object CartManager {
    private const val CART_PREFS = "cart_prefs"
    private const val CART_ITEMS_KEY = "cart_items"

    fun addToCart(context: Context, productId: String) {
        val prefs = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE)
        val items = prefs.getStringSet(CART_ITEMS_KEY, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        items.add(productId)
        prefs.edit().putStringSet(CART_ITEMS_KEY, items).apply()
    }

    fun getCartItems(context: Context): Set<String> {
        val prefs = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE)
        return prefs.getStringSet(CART_ITEMS_KEY, emptySet()) ?: emptySet()
    }
}