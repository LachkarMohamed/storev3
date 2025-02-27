package com.example.storeai.data.api


import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.Response
import com.example.storeai.data.model.RegisterResponse
import com.example.storeai.data.model.Product
import com.example.storeai.data.model.RegisterRequest
import com.example.storeai.data.model.User
import com.example.storeai.data.model.LoginRequest
import com.example.storeai.data.model.LoginResponse





interface ApiService {
    @GET("products")
    suspend fun getAllProducts(): Response<List<Product>>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: String): Response<Product>

    @POST("users")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("users/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>


}