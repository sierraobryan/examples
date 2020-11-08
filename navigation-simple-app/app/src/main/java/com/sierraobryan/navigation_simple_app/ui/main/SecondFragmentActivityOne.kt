package com.sierraobryan.navigation_simple_app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sierraobryan.navigation_simple_app.R
import com.sierraobryan.navigation_simple_app.databinding.FragmentSecondMainBinding

class SecondFragmentActivityOne : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSecondMainBinding.inflate(inflater)
        val args: SecondFragmentActivityOneArgs by navArgs()
        binding.button.setOnClickListener {
            navigateToThirdFragmentPopTwo()
        }
        binding.button2.setOnClickListener {
            navigateToThirdFragment()
        }
        binding.button3.setOnClickListener {
            navigateToThirdFragmentClearBackStack()
        }
        binding.button4.setOnClickListener {
            navigateToThirdFragmentWithArg()
        }
        binding.fragmentName.text = this::class.java.simpleName
        binding.safeArgValue.text = args.longArgument.toString()
        return binding.root
    }

    // normal navigation
    private fun navigateToThirdFragment() {
        findNavController().navigate(R.id.action_secondFragment_to_thirdFragment)
    }

    // popUpTo fragment one
    private fun navigateToThirdFragmentPopTwo() {
        findNavController().navigate(R.id.action_secondFragment_to_thirdFragment_2)
    }

    // clear back stack
    private fun navigateToThirdFragmentClearBackStack() {
        findNavController().navigate(R.id.action_secondFragment_to_thirdFragment_3)
    }

    private fun navigateToThirdFragmentWithArg() {
        findNavController().navigate(
            SecondFragmentActivityOneDirections
                .actionSecondFragmentToThirdFragment("a string")
        )
    }

}