package com.sierraobryan.wwcode_accessibility

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.accessibility.AccessibilityChecks
import com.sierraobryan.wwcode_accessibility.ui.main.MainScreen
import com.sierraobryan.wwcode_accessibility.ui.theme.MyApplicationTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// file: app/src/androidTest/java/com/package/MyComposeTest.kt

class MyComposeTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

//    init {
//        AccessibilityChecks.enable()
//    }

    @Test
    fun MyTest() {
        // Start the app
        //composeTestRule.onNodeWithText("FETCH COMMITS").performClick()

    }
}