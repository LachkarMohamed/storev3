package com.example.storeai.ui.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.storeai.R
import com.example.storeai.adapters.ProductAdapter
import com.example.storeai.databinding.FragmentMainCategoryBinding
import com.example.storeai.ui.home.HomeViewModel
import com.example.storeai.ui.home.HomeFragmentDirections
import androidx.navigation.fragment.findNavController


class MainCategoryFragment : Fragment(R.layout.fragment_main_category) {
    private lateinit var binding: FragmentMainCategoryBinding
    private lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainCategoryBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        setupRecyclers()
        observeProducts()
    }

    private fun setupRecyclers() {
        binding.rvSpecialProducts.layoutManager = GridLayoutManager(context, 2)
        binding.rvBestDealsProducts.layoutManager = GridLayoutManager(context, 2)
        binding.rvBestProducts.layoutManager = GridLayoutManager(context, 2)
    }

    // MainCategoryFragment.kt
    private fun observeProducts() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            binding.rvSpecialProducts.adapter = ProductAdapter(products.shuffled().take(4)) { productId ->
                navigateToDetail(productId)
            }
            binding.rvBestDealsProducts.adapter = ProductAdapter(products.shuffled().take(4)) { productId ->
                navigateToDetail(productId)
            }
            binding.rvBestProducts.adapter = ProductAdapter(products) { productId ->
                navigateToDetail(productId)
            }
        }
    }

    private fun navigateToDetail(productId: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(productId)
        findNavController().navigate(action)
    }
}