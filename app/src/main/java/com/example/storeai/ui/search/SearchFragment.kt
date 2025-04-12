package com.example.storeai.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.storeai.adapters.ProductAdapter
import com.example.storeai.databinding.FragmentSearchBinding
import com.example.storeai.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchListener()
        observeProducts()
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter(emptyList()) { productId ->
            // Navigate to product detail
            val action = SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(productId)
            findNavController().navigate(action)
        }
        binding.rvSearchResults.adapter = adapter
        binding.rvSearchResults.layoutManager = GridLayoutManager(requireContext(), 3)
    }

    private fun observeProducts() {
        viewModel.filteredProducts.observe(viewLifecycleOwner) { products ->
            if (products.isEmpty()) {
                binding.tvEmptyResults.visibility = View.VISIBLE
                binding.rvSearchResults.visibility = View.GONE
            } else {
                binding.tvEmptyResults.visibility = View.GONE
                binding.rvSearchResults.visibility = View.VISIBLE
                adapter.updateProducts(products)
            }
        }
    }

    private fun setupSearchListener() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    viewModel.searchProducts(it.toString())
                }
            }
        })
    }
}