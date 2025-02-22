package com.example.storeai.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeai.data.model.RegisterRequest
import com.example.storeai.data.model.RegisterResponse
import com.example.storeai.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Idle)
    val registrationState: StateFlow<RegistrationState> = _registrationState

    fun registerUser(firstName: String, lastName: String, email: String, password: String) {
        val username = "$firstName $lastName"
        val request = RegisterRequest(username, email, password)

        _registrationState.value = RegistrationState.Loading
        viewModelScope.launch {
            try {
                val response = userRepository.registerUser(request)
                // Save token and user data here
                _registrationState.value = RegistrationState.Success(response)
            } catch (e: Exception) {
                _registrationState.value = RegistrationState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class RegistrationState {
    object Idle : RegistrationState()
    object Loading : RegistrationState()
    data class Success(val response: RegisterResponse) : RegistrationState()
    data class Error(val message: String) : RegistrationState()
}