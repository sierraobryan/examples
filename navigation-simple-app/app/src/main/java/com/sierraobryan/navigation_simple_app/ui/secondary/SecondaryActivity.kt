package com.sierraobryan.navigation_simple_app.ui.secondary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.sierraobryan.navigation_simple_app.R

class SecondaryActivity : AppCompatActivity() {

    enum class SCREEN {
        FIRST_SCREEN,
        SECOND_SCREEN
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondardy)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.secondary_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        when (intent.getSerializableExtra(SCREEN_ARG) as SCREEN?) {
            SCREEN.FIRST_SCREEN -> {
                navController.navigate(R.id.action_emptyFragment_to_firstFragment)
            }
            SCREEN.SECOND_SCREEN -> {
                navController.navigate(R.id.action_emptyFragment_to_secondFragment)
            }
        }
    }

    companion object {
        const val SCREEN_ARG = "screen"
    }

}