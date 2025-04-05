package com.example.storeai.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.storeai.R
import com.example.storeai.data.model.Product
import com.example.storeai.databinding.ItemProductBinding

class ProductAdapter(
    var products: List<Product>,
    private val onProductClick: (String) -> Unit // Changed to accept productId
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        with(holder.binding) {
            productTitle.text = product.title
            productPrice.text = "$${"%.2f".format(product.price)}"

            productImage.load(product.image) {
                crossfade(true)
                placeholder(R.drawable.placeholder_image)
                error(R.drawable.error_image)
            }

            root.setOnClickListener {
                onProductClick(product.id) // Pass productId to lambda
            }
        }
    }

    override fun getItemCount() = products.size
}