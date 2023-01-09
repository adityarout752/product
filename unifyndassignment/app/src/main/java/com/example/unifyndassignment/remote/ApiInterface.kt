package com.example.unifyndassignment.remote

import com.example.unifyndassignment.model.auth.AuthRequestModel
import com.example.unifyndassignment.model.auth.AuthResponseModel
import com.example.unifyndassignment.model.product.ProductModel
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET("/products")
    suspend fun getProductList(
        @Query("limit") limit: Int = 10,
        @Query("skip") skip: Int = 10
    ): Response<ProductModel>


    @Headers("Content-Type: application/json")
    @POST("/auth/login")
    suspend fun loginProduct(
        @Body authRequestModel: AuthRequestModel
    ): Response<AuthResponseModel>
}