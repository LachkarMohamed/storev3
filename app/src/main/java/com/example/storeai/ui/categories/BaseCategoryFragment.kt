package com.example.storeai.ui.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.storeai.R
import com.example.storeai.adapters.ProductAdapter
import com.example.storeai.databinding.FragmentBaseCategoryBinding
import com.example.storeai.ui.home.HomeViewModel

class BaseCategoryFragment : Fragment(R.layout.fragment_base_category) {
    companion object {
        private const val ARG_CATEGORY_ID = "category_id"

        fun newInstance(categoryId: String): BaseCategoryFragment {
            val fragment = BaseCategoryFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY_ID, categoryId)
            fragment.arguments = args
            return fragment
        }
    }
    private lateinit var binding: FragmentBaseCategoryBinding
    private lateinit var viewModel: HomeViewModel
    private var categoryId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryId = arguments?.getString("categoryId")
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBaseCategoryBinding.bind(view)

        setupRecyclers()
        observeProducts()
    }

    private fun setupRecyclers() {
        binding.rvOfferProducts.layoutManager = GridLayoutManager(context, 2)
        binding.rvBestProducts.layoutManager = GridLayoutManager(context, 2)
    }

    private fun observeProducts() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            val filteredProducts = products.filter { it.categorie_id == categoryId }
            binding.rvOfferProducts.adapter = ProductAdapter(filteredProducts) { navigateToDetail(it.id) }
            binding.rvBestProducts.adapter = ProductAdapter(filteredProducts.shuffled().take(4)) { navigateToDetail(it.id) }
        }
    }

    private fun navigateToDetail(productId: String) {
        // Implement navigation to product detail
    }
}