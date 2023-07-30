package com.example.sampleapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sampleapp.databinding.FragmentShowDataBinding

class ShowDataFragment : Fragment() {

    private lateinit var binding: FragmentShowDataBinding
    private var title = ""

    companion object {
        const val TAG = "Data"

        fun newInstance(data : String) : ShowDataFragment{
            val fragment = ShowDataFragment()
            val args = Bundle()
            args.putString(TAG, data)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = arguments?.getString(TAG, "") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentShowDataBinding.inflate(inflater, container, false)
        binding.data.text = title
            return binding.root
    }

}