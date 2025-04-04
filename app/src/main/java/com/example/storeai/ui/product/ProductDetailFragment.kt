package com.example.storeai.ui.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storeai.R
import com.example.storeai.adapters.ProductAdapter
import com.example.storeai.databinding.FragmentProductDetailBinding
import com.example.storeai.viewmodels.ProductViewModel

class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {
    private lateinit var binding: FragmentProductDetailBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var similarAdapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductDetailBinding.bind(view)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        setupUI()
        observeData()
        loadProduct()
    }

    private fun setupUI() {
        similarAdapter = ProductAdapter(emptyList()) { navigateToDetail(it.id) }
        binding.rvSimilarProducts.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = similarAdapter
        }
    }

    private fun observeData() {
        viewModel.product.observe(viewLifecycleOwner) { product ->
            product?.let {
                binding.productTitle.text = it.title
                binding.productPrice.text = "$${it.price}"
                binding.productDescription.text = it.description
                // Load image using Coil/Glide

                it.similar_products_ids?.forEach { id ->
                    viewModel.loadSimilarProduct(id)
                }
            }
        }

        viewModel.similarProducts.observe(viewLifecycleOwner) { products ->
            similarAdapter.products = products
        }
    }

    private fun loadProduct() {
        val productId = arguments?.getString("productId") ?: return
        viewModel.loadProductDetails(productId)
    }

    private fun navigateToDetail(productId: String) {
        // Implement navigation to product detail
    }
}