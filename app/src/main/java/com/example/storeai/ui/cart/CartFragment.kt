package com.example.storeai.ui.cart

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storeai.databinding.FragmentCartBinding
import com.example.storeai.utils.CartManager
import com.example.storeai.R
import com.example.storeai.adapters.CartAdapter
import com.example.storeai.ui.cart.CartViewModel

class CartFragment : Fragment(R.layout.fragment_cart) {
    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCartBinding.bind(view)

        setupRecyclerView()
        observeCart()
        setupCloseButton()
        setupCheckoutButton()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            onIncrease = { productId ->
                CartManager.updateQuantity(requireContext(), productId,
                    CartManager.getCartItems(requireContext())
                        .find { it.productId == productId }?.quantity?.plus(1) ?: 1
                )
            },
            // Update the onDecrease lambda in setupRecyclerView()
            onDecrease = { productId ->
                val currentQuantity = CartManager.getCartItems(requireContext())
                    .find { it.productId == productId }?.quantity ?: 1

                if(currentQuantity > 1) {
                    CartManager.updateQuantity(requireContext(), productId, currentQuantity - 1)
                }
            },
            onRemove = { productId ->
                CartManager.removeFromCart(requireContext(), productId)
            }
        )

        binding.rvCart.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }
    }

    private fun observeCart() {
        CartManager.cartItems.observe(viewLifecycleOwner) { items ->
            cartAdapter.submitList(items)
            binding.layoutCartEmpty.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        }

        CartManager.totalPrice.observe(viewLifecycleOwner) { total ->
            binding.tvTotalPrice.text = "$${"%.2f".format(total ?: 0.0)}"
        }
    }

    private fun setupCloseButton() {
        binding.imageCloseCart.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupCheckoutButton() {
        binding.buttonCheckout.setOnClickListener {
            // Decorative only
            Toast.makeText(context, "Checkout is not implemented yet", Toast.LENGTH_SHORT).show()
        }
    }
}