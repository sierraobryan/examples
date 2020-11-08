package com.sierraobryan.navigation_simple_app.ui.secondary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sierraobryan.navigation_simple_app.databinding.FragmentSecondaryBinding

class SecondFragmentActivityTwo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSecondaryBinding.inflate(inflater)
        binding.fragmentName.text = this::class.java.simpleName
        return binding.root
    }

}