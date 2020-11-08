package com.sierraobryan.navigation_simple_app.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sierraobryan.navigation_simple_app.R
import com.sierraobryan.navigation_simple_app.databinding.FragmentFirstMainBinding
import com.sierraobryan.navigation_simple_app.ui.menus.ThirdActivity
import com.sierraobryan.navigation_simple_app.ui.secondary.SecondaryActivity

class FirstFragmentActivityOne: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFirstMainBinding.inflate(inflater)
        binding.button.setOnClickListener {
            navigateToSecondFragmentWithDynamicArg()
        }
        binding.button2.setOnClickListener {
            navigateToSecondFragmentWithArg()
        }
        binding.button3.setOnClickListener {
            navigateToSecondFragmentWithAnimation()
        }
        binding.button4.setOnClickListener {
            navigateToSecondFragmentWithAnimationTwo()
        }
        binding.button5.setOnClickListener {
            navigateToFragmentOneInNewActivity()
        }
        binding.button6.setOnClickListener {
            navigateToFragmentTwoInNewActivity()
        }
        binding.button7.setOnClickListener {
            navigateToEmptyFragmentInNewActivity()
        }
        binding.button8.setOnClickListener {
            navigateToMenus()
        }
        binding.fragmentName.text = this::class.java.simpleName
        return binding.root
    }

    private fun navigateToSecondFragmentWithArg() {
        findNavController().navigate(R.id.action_firstFragment_to_secondFragment_withArg)
    }

    private fun navigateToSecondFragmentWithDynamicArg() {
        findNavController().navigate(
            FirstFragmentActivityOneDirections
                .actionFirstFragmentToSecondFragmentWithArg(101L)
        )
    }

    private fun navigateToSecondFragmentWithAnimation() {
        findNavController().navigate(R.id.action_firstFragment_to_secondFragment_withAnimation)
    }

    private fun navigateToSecondFragmentWithAnimationTwo() {
        findNavController().navigate(R.id.action_firstFragment_to_secondFragment_withAnimation_2)
    }

    private fun navigateToEmptyFragmentInNewActivity() {
        val intent = Intent(context, SecondaryActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToFragmentOneInNewActivity() {
        val intent = Intent(context, SecondaryActivity::class.java)
        intent.putExtra(SecondaryActivity.SCREEN_ARG, SecondaryActivity.SCREEN.FIRST_SCREEN)
        startActivity(intent)
    }

    private fun navigateToFragmentTwoInNewActivity() {
        val intent = Intent(context, SecondaryActivity::class.java)
        intent.putExtra(SecondaryActivity.SCREEN_ARG, SecondaryActivity.SCREEN.SECOND_SCREEN)
        startActivity(intent)
    }

    private fun navigateToMenus() {
        val intent = Intent(context, ThirdActivity::class.java)
        startActivity(intent)
    }

}