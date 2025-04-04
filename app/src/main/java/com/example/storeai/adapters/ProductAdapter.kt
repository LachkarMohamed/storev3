package com.example.storeai.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.storeai.data.model.Product
import com.example.storeai.databinding.ItemProductBinding


class ProductAdapter(
    var products: List<Product>,
    private val onClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.binding.productTitle.text = product.title
        holder.binding.productPrice.text = "$${product.price}"

        // Load image with Coil
        holder.binding.productImage.load(
            product.image
        )

        holder.itemView.setOnClickListener { onClick(product) }
    }

    override fun getItemCount() = products.size
}