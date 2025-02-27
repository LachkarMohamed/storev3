package com.example.storeai.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeai.data.model.LoginRequest
import com.example.storeai.data.model.LoginResponse
import com.example.storeai.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun loginUser(email: String, password: String) {
        val request = LoginRequest(email, password)

        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {
                val response = userRepository.loginUser(request)
                _loginState.value = LoginState.Success(response)
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Login failed")
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val response: LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}