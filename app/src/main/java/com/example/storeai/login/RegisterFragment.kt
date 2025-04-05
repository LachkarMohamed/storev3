package com.example.storeai.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.storeai.databinding.FragmentRegisterBinding
import com.example.storeai.data.repository.UserRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.Lifecycle
import com.example.storeai.data.model.RegisterResponse
import com.example.storeai.R // For navigation IDs
import com.example.storeai.activities.ShoppingActivity


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(
            UserRepository()
        )
    }

    // Add ViewModel Factory class
    class RegisterViewModelFactory(
        private val userRepository: UserRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegisterViewModel(userRepository) as T
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registrationState.collect { state ->
                    when (state) {
                        is RegistrationState.Loading -> showLoading()
                        is RegistrationState.Success -> handleSuccess(state.response)
                        is RegistrationState.Error -> handleError(state.message)
                        RegistrationState.Idle -> Unit
                    }
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.buttonRegisterRegister.setOnClickListener {
            val firstName = binding.edFirstNameRegister.text.toString().trim()
            val lastName = binding.edLastNameRegister.text.toString().trim()
            val email = binding.edEmailRegister.text.toString().trim()
            val password = binding.edPasswordRegister.text.toString().trim()

            if (validateInputs(firstName, lastName, email, password)) {
                viewModel.registerUser(firstName, lastName, email, password)
            }

        }
        binding.tvDoYouHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }



    private fun handleSuccess(response: RegisterResponse) {
        hideLoading()
        saveAuthData(response)
        navigateToHome()
    }

    private fun saveAuthData(response: RegisterResponse) {
        val sharedPref = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("auth_token", response.token)
            putString("user_id", response.user.id)
            apply()
        }
    }

    // Inside handleSuccess()
    private fun navigateToHome() {
        startActivity(Intent(requireContext(), ShoppingActivity::class.java))
        requireActivity().finish() // Close registration flow
    }

    private fun handleError(message: String) {
        hideLoading()
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading() {
        // Implement your loading UI (e.g., show progress bar)
        binding.buttonRegisterRegister.isEnabled = false
    }

    private fun hideLoading() {
        // Implement loading dismiss
        binding.buttonRegisterRegister.isEnabled = true
    }

    private fun validateInputs(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Boolean {
        return when {
            firstName.isEmpty() -> {
                showToast("First name required")
                false
            }
            lastName.isEmpty() -> {
                showToast("Last name required")
                false
            }
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

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}