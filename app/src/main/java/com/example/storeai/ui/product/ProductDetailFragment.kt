package com.example.storeai.ui.product

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.storeai.R
import com.example.storeai.adapters.ProductAdapter
import com.example.storeai.databinding.FragmentProductDetailBinding
import com.example.storeai.viewmodels.ProductViewModel
import com.example.storeai.ui.product.ProductDetailFragmentArgs // Safe Args import
import com.example.storeai.ui.product.ProductDetailFragmentDirections // Safe Args import
import androidx.recyclerview.widget.GridLayoutManager
import com.example.storeai.utils.CartManager

class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {
    private lateinit var binding: FragmentProductDetailBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var similarAdapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductDetailBinding.bind(view)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        setupUI()
        setupCloseButton()
        observeData()
        loadProduct()
        binding.btnAddToCart.setOnClickListener {
            viewModel.product.value?.let { product ->
                CartManager.addToCart(requireContext(), product)
                // Show confirmation (optional)
                Toast.makeText(context, "${product.title} added to cart", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // ProductDetailFragment.kt
    private fun setupUI() {
        // Update layout manager for grid
        binding.rvSimilarProducts.layoutManager = GridLayoutManager(context, 2)
        similarAdapter = ProductAdapter(emptyList()) { productId ->
            val action = ProductDetailFragmentDirections.actionProductDetailFragmentSelf(productId)
            findNavController().navigate(action)
        }
        binding.rvSimilarProducts.adapter = similarAdapter
    }

    private fun setupCloseButton() {
        binding.ivClose.setOnClickListener {
            // Navigate back to home
            findNavController().navigate(R.id.homeFragment)
        }
    }

    private fun observeData() {
        viewModel.product.observe(viewLifecycleOwner) { product ->
            product?.let {
                binding.productTitle.text = it.title
                binding.productPrice.text = "$${"%.2f".format(it.price)}"
                binding.productDescription.text = it.description

                binding.productImage.load(it.image) {
                    crossfade(true)
                    placeholder(R.drawable.placeholder_image)
                    error(R.drawable.error_image)
                }

                it.similar_products_ids?.let { ids ->
                    viewModel.loadSimilarProducts(ids)
                }
            }
        }

        viewModel.similarProducts.observe(viewLifecycleOwner) { products ->
            similarAdapter.updateProducts(products)
        }
    }

    private fun loadProduct() {
        // Use Safe Args to get productId
        val args = ProductDetailFragmentArgs.fromBundle(requireArguments())
        val productId = args.productId
        viewModel.loadProductDetails(productId)
    }
}