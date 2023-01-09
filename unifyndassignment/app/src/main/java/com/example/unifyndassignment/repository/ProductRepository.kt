package com.example.unifyndassignment.repository

import com.example.unifyndassignment.model.auth.AuthRequestModel
import com.example.unifyndassignment.model.auth.AuthResponseModel
import com.example.unifyndassignment.model.product.ProductModel
import com.example.unifyndassignment.remote.ApiInterface
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import javax.inject.Inject

@ActivityRetainedScoped
class ProductRepository @Inject constructor(
    private val productApi: ApiInterface
) {
    suspend fun getProductsList(limit:Int,skip:Int): Response<ProductModel> {
        return productApi.getProductList(limit, skip)
    }

    suspend fun loginPeoduct(authRequestModel: AuthRequestModel):Response<AuthResponseModel> {
        return productApi.loginProduct(authRequestModel)
    }
}