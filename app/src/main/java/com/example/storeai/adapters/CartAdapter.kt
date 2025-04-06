package com.example.storeai.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.storeai.R
import com.example.storeai.databinding.CartProductItemBinding
import com.example.storeai.utils.CartManager

class CartAdapter(
    private val onIncrease: (String) -> Unit,
    private val onDecrease: (String) -> Unit,
    private val onRemove: (String) -> Unit
) : ListAdapter<CartManager.CartItem, CartAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(val binding: CartProductItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<CartManager.CartItem>() {
        override fun areItemsTheSame(oldItem: CartManager.CartItem, newItem: CartManager.CartItem) =
            oldItem.productId == newItem.productId

        override fun areContentsTheSame(oldItem: CartManager.CartItem, newItem: CartManager.CartItem) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartProductItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tvProductCartName.text = item.title
            tvProductCartPrice.text = "$${"%.2f".format(item.price)}"
            tvCartProductQuantity.text = item.quantity.toString()

            productImage.load(item.image) {
                crossfade(true)
                placeholder(R.drawable.placeholder_image)
            }

            imagePlus.setOnClickListener { onIncrease(item.productId) }
            imageMinus.setOnClickListener { onDecrease(item.productId) }
            imageCloseCart.setOnClickListener { onRemove(item.productId) }
        }
    }
}