package com.example.unifyndassignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.unifyndassignment.model.auth.AuthRequestModel
import com.example.unifyndassignment.model.auth.AuthResponseModel
import com.example.unifyndassignment.model.product.Product
import com.example.unifyndassignment.model.product.ProductModel
import com.example.unifyndassignment.remote.NetworkResult
import com.example.unifyndassignment.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    application: Application
) : AndroidViewModel(application) {

    //product
    private var productRespone = MutableLiveData<NetworkResult<ProductModel>>()
    var observeProductResponse: LiveData<NetworkResult<ProductModel>> = productRespone

    //authentication
    private var loginResponse = MutableLiveData<NetworkResult<AuthResponseModel>>()
    var observeLoginResponse: LiveData<NetworkResult<AuthResponseModel>> = loginResponse

    //sharing data
    private var sharedData=MutableLiveData<Product>()
    var observeSharedData:LiveData<Product> = sharedData

    fun getProductList(limit:Int,skip:Int) = viewModelScope.launch {
        try {
            val response = productRepository.getProductsList(limit, skip)
            if (response.isSuccessful) {
                productRespone.postValue(handleProductResponse(response))
            }


        } catch (e: Exception) {
            productRespone.value = NetworkResult.Error("Product Not Found")
        }
    }

    private fun handleProductResponse(response: Response<ProductModel>): NetworkResult<ProductModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return NetworkResult.Success(resultResponse)
            }
        }
        return NetworkResult.Error(response.message())


    }


    fun loginProduct(authRequestModel: AuthRequestModel) = viewModelScope.launch {
        try {
            val response = productRepository.loginPeoduct(authRequestModel)
            if (response.isSuccessful) {
                loginResponse.postValue(handleLoginResponse(response))
            }


        } catch (e: Exception) {
            loginResponse.value = NetworkResult.Error("Product Not Found")
        }
    }

    private fun handleLoginResponse(response: Response<AuthResponseModel>): NetworkResult<AuthResponseModel>? {
        if (response.isSuccessful) {
            response.body()?.let { loginresponse ->
                return NetworkResult.Success(loginresponse)
            }
        }
        return NetworkResult.Error(response.message())
    }

    fun sendDataFragments(product: Product){
        sharedData.value =product
    }

}

