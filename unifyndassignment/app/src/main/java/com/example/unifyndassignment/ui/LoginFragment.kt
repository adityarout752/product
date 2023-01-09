package com.example.unifyndassignment.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.unifyndassignment.R
import com.example.unifyndassignment.databinding.FragmentLoginBinding
import com.example.unifyndassignment.model.auth.AuthRequestModel
import com.example.unifyndassignment.remote.NetworkResult
import com.example.unifyndassignment.viewmodel.ProductViewModel
import com.google.android.material.snackbar.Snackbar


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        productViewModel = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        authentication()
        return binding.root
    }


    private fun authentication() {
        binding.btnLogin.setOnClickListener {
            if (binding.etvUsername.text.isNotEmpty() && binding.etvPassword.text.isNotEmpty()) {
                val authRequestModel = AuthRequestModel(
                    binding.etvUsername.text.toString(),
                    binding.etvPassword.text.toString()
                )
                productViewModel.loginProduct(authRequestModel)
                productViewModel.observeLoginResponse.observe(requireActivity()) { response ->
                    Log.d("AUTH", "${response.data}")
                    when (response) {

                        is NetworkResult.Success -> {

                            activity?.supportFragmentManager?.beginTransaction()?.apply {
                                replace(R.id.fl_container, ProductDetailsFragment())
                                commit()
                                addToBackStack(null)
                            }
                        }
                        is NetworkResult.Error -> {
                            Toast.makeText(requireContext(),"There is something wrong",Toast.LENGTH_SHORT).show()
                        }

                        else -> {}
                    }

                }

            } else {
                binding.etvUsername.error = "username field cannot be empty"
                binding.etvPassword.error = " password field cannot be empty"
            }
        }


    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}