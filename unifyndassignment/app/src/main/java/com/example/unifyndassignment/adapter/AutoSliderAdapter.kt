package com.example.unifyndassignment.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import coil.load
import com.example.unifyndassignment.databinding.AutoslideItrmLayoutBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class AutoSliderAdapter(val mcontext: Context, val sliderList: List<String>) :
    SliderViewAdapter<AutoSliderAdapter.SliderViewHolder>() {

    inner class SliderViewHolder(itemView: AutoslideItrmLayoutBinding) :
        SliderViewAdapter.ViewHolder(itemView.root) {
        var imageView: ImageView = itemView.ivImage
    }

    override fun getCount(): Int {

        return sliderList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder {
        Log.d("xyt","size")
        val itemview =
            AutoslideItrmLayoutBinding.inflate(LayoutInflater.from(mcontext), parent, false)
        return SliderViewHolder(itemview)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder?, position: Int) {
        viewHolder?.imageView?.load(sliderList[position])
    }


}