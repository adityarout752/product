package com.example.unifyndassignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.unifyndassignment.adapter.AutoSliderAdapter
import com.example.unifyndassignment.databinding.FragmentProductDetailsBinding
import com.example.unifyndassignment.viewmodel.ProductViewModel
import com.smarteist.autoimageslider.SliderView
import java.util.*


class ProductDetailsFragment : Fragment() {
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    lateinit var productViewModel: ProductViewModel
    lateinit var sliderAdapter: AutoSliderAdapter
    lateinit var sliderView: SliderView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        productViewModel = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        setData()
        return binding.root
    }

    private fun setData() {
        sliderView = binding.imageSlider2
        productViewModel.observeSharedData.observe(requireActivity()) {
            it?.let {
                sliderAdapter = AutoSliderAdapter(requireContext(), it.images)
                sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
                sliderView.setSliderAdapter(sliderAdapter)
                sliderView.isAutoCycle = true
                binding.tvTime.text = "${Calendar.getInstance().time}"
                binding.tvTitles.text = it.title
                binding.tvDescription.text = it.description
            }
        }

        binding.ivCross.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}