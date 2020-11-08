package com.sierraobryan.navigation_simple_app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sierraobryan.navigation_simple_app.R
import com.sierraobryan.navigation_simple_app.databinding.FragmentThirdMainBinding

class ThirdFragmentActivityOne : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentThirdMainBinding.inflate(inflater)
        binding.button.setOnClickListener {
            navigateToFirstFragmentPopAllInclusiveFalse()
        }
        binding.button2.setOnClickListener {
            navigateToFirstFragmentAllOnBackStack()
        }
        binding.button3.setOnClickListener {
            navigateToFirstFragmentPopAll()
        }
        binding.fragmentName.text = this::class.java.simpleName
        binding.safeArgValue.text = arguments?.get("nonSafeArg") as String?
        return binding.root
    }

    // normal navigation
    private fun navigateToFirstFragmentAllOnBackStack() {
        findNavController().navigate(R.id.action_thirdFragment_to_firstFragment)
    }

    // popUpTo fragment one, inclusive false
    private fun navigateToFirstFragmentPopAllInclusiveFalse() {
        findNavController().navigate(R.id.action_thirdFragment_to_firstFragment_2)
    }

    // popUpTo fragment one
    private fun navigateToFirstFragmentPopAll() {
        findNavController().navigate(R.id.action_thirdFragment_to_firstFragment_3)
    }
}