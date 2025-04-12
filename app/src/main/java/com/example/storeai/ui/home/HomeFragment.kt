package com.example.storeai.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.storeai.R
import com.example.storeai.adapters.HomeViewpagerAdapter
import com.example.storeai.data.model.Category
import com.example.storeai.databinding.FragmentHomeBinding
import com.example.storeai.ui.categories.BaseCategoryFragment
import com.example.storeai.ui.categories.MainCategoryFragment
import com.example.storeai.ui.home.HomeFragmentDirections // Add Safe Args import
import com.google.android.material.tabs.TabLayoutMediator
import androidx.appcompat.app.AlertDialog
import dagger.hilt.android.AndroidEntryPoint
import com.example.storeai.adapters.ProductAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            setupViewPager(categories)
        }

        binding.searchBar.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_homeFragment_to_searchFragment)
        }

        binding.ivScan.setOnClickListener {
            showImageSourceDialog()
        }
    }

    private fun setupViewPager(categories: List<Category>) {
        val fragments = mutableListOf<Fragment>().apply {
            add(MainCategoryFragment())
            categories.forEach { category ->
                add(BaseCategoryFragment.newInstance(category.id))
            }
        }

        val adapter = HomeViewpagerAdapter(
            fragments,
            childFragmentManager,
            lifecycle
        )

        binding.viewpagerHome.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewpagerHome) { tab, position ->
            tab.text = when(position) {
                0 -> "Main"
                else -> categories[position-1].title
            }
        }.attach()
    }

    private fun setupProductAdapter() {
        // Example for MainCategoryFragment's adapter setup
        val adapter = ProductAdapter(emptyList()) { productId ->
            val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(productId)
            findNavController().navigate(action)
        }
        // Assign to RecyclerView
    }


    private fun showImageSourceDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery", "Cancel")
        AlertDialog.Builder(requireContext()).apply {
            setItems(options) { _, which ->
                when(which) {
                    0 -> takePhoto()
                    1 -> pickFromGallery()
                }
            }
        }.show()
    }

    private fun takePhoto() {
        // Implement camera intent
    }

    private fun pickFromGallery() {
        // Implement gallery picker
    }
}