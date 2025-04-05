package com.example.storeai.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.storeai.R
import androidx.lifecycle.Lifecycle
import com.example.storeai.data.repository.UserRepository
import com.example.storeai.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch
import com.example.storeai.data.model.LoginResponse
import com.example.storeai.activities.ShoppingActivity


class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(UserRepository())
    }

    class LoginViewModelFactory(
        private val userRepository: UserRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(userRepository) as T
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect { state ->
                    when (state) {
                        is LoginState.Loading -> showLoading()
                        is LoginState.Success -> handleSuccess(state.response)
                        is LoginState.Error -> handleError(state.message)
                        LoginState.Idle -> Unit
                    }
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.buttonLoginLogin.setOnClickListener {
            val email = binding.edEmailLogin.text.toString().trim()
            val password = binding.edPasswordLogin.text.toString().trim()

            if (validateInputs(email, password)) {
                viewModel.loginUser(email, password)
            }
        }

        binding.tvDontHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                showToast("Email required")
                false
            }
            password.isEmpty() -> {
                showToast("Password required")
                false
            }
            else -> true
        }
    }

    private fun handleSuccess(response: LoginResponse) {
        saveAuthData(response)
        navigateToHome()
    }

    private fun saveAuthData(response: LoginResponse) {
        val sharedPref = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("auth_token", response.token)
            putString("user_id", response.user.id)
            apply()
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(requireContext(), ShoppingActivity::class.java))
        requireActivity().finish() // Close login flow
    }

    private fun showLoading() {
        binding.buttonLoginLogin.isEnabled = false
        // Show progress indicator
    }

    private fun handleError(message: String) {
        binding.buttonLoginLogin.isEnabled = true
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}