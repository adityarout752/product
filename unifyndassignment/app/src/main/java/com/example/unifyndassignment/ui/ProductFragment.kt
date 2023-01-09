package com.example.unifyndassignment.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unifyndassignment.R
import com.example.unifyndassignment.adapter.ProductAdapter
import com.example.unifyndassignment.databinding.FragmentProductBinding
import com.example.unifyndassignment.model.product.Product
import com.example.unifyndassignment.model.product.ProductModel
import com.example.unifyndassignment.utils.OnItemClicked
import com.example.unifyndassignment.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class ProductFragment : Fragment(), OnItemClicked {
    lateinit var productViewModel: ProductViewModel
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!
    private var limit = 10;
    private var skip = 10;
    private var total = 0;
    private var productModel: ProductModel? = null
    lateinit var productAdapter: ProductAdapter
    private var isLoading = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductBinding.inflate(inflater, container, false)
        productViewModel =
            ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        setRv()
        setRvScrollListener();
        searchproducts()
        return binding.root
    }


    private fun setRvScrollListener() {
        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == productModel!!.products.size - 1) {
                        //bottom of list!
                        loadMore()
                        isLoading = true
                    }
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadMore() {
        productModel = null
        val handler = Handler()
        handler.postDelayed({
            limit += 10;
            if (limit <= total) {

                productViewModel.getProductList(limit, skip)
                isLoading = false
                productAdapter.notifyDataSetChanged()
            }

        }, 500)
    }

    private fun searchproducts() {
        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterSearch(newText)
                return false
            }


        })


    }

    private fun filterSearch(newText: String?) {
        val filteredlist: MutableList<Product> = mutableListOf()

        for (i in productModel!!.products) {
            if (i.description.toLowerCase(Locale.ROOT)
                    .contains(newText!!.toLowerCase(Locale.ROOT))
            ) {

                filteredlist.add(i)
            }
        }

        if (filteredlist.isEmpty()) {

            binding.recyclerview.visibility = View.GONE
            binding.noLayout.visibility = View.VISIBLE
        } else {

            productAdapter.filterList(filteredlist)
        }
    }

    private fun setRv() {

        productViewModel.getProductList(limit, skip)
        productViewModel.observeProductResponse.observe(requireActivity()) {
            productModel = it.data!!
            total = productModel!!.total
            productAdapter =
                ProductAdapter(requireContext(), it.data.products, this@ProductFragment)
            binding.recyclerview.adapter = productAdapter
        }
    }

    override fun sendData(product: Product) {
        val loginFragment = LoginFragment()
        productViewModel.sendDataFragments(product)

        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fl_container, loginFragment)
            commit()
            addToBackStack(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}