package com.example.unifyndassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.unifyndassignment.databinding.ProductItemLayoutBinding
import com.example.unifyndassignment.model.product.Product
import com.example.unifyndassignment.utils.OnItemClicked
import com.smarteist.autoimageslider.SliderView

class ProductAdapter(val context: Context, var productModel: List<Product>, val onItemClickedListener: OnItemClicked) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    lateinit var sliderAdapter: AutoSliderAdapter
    inner class ProductViewHolder(val binding: ProductItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ProductItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val binding = holder.binding
        val productList = productModel[position]
       val imageslider:SliderView=binding.imageSlider
        sliderAdapter= AutoSliderAdapter(context,productList.images)
        imageslider.setSliderAdapter(sliderAdapter)
        imageslider.autoCycleDirection=SliderView.LAYOUT_DIRECTION_LTR
        imageslider.isAutoCycle = true
        binding.root.setOnClickListener {
          onItemClickedListener.sendData(productList)
        }
        binding.titleing.text = productList.brand
        binding.desc.text = productList.description

    }

    override fun getItemCount(): Int {
        return productModel.size
    }

    fun filterList(filterlist: List<Product>) {

        productModel = filterlist

        notifyDataSetChanged()
    }
}