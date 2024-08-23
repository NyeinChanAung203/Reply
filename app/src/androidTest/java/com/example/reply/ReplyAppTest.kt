package com.example.reply

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import com.example.reply.ui.ReplyApp
import org.junit.Rule
import org.junit.Test

class ReplyAppTest {
    @get:Rule
    val compostTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    @TestCompactWidth
    fun compactDevice_verifyUsingBottomNavigation(){
        compostTestRule.setContent { 
            ReplyApp(windowSize = WindowWidthSizeClass.Compact)
        }
        compostTestRule.onNodeWithTag(
            compostTestRule.activity.getString(R.string.navigation_bottom)
        ).assertExists()
    }

    @Test
    @TestMediumWidth
    fun mediumDevice_verifyUsingNavigationRail(){
        compostTestRule.setContent { 
            ReplyApp(windowSize = WindowWidthSizeClass.Medium)
        }
        compostTestRule.onNodeWithTag(
            compostTestRule.activity.getString(R.string.navigation_rail)
        ).assertExists()
    }

    @Test
    @TestExpandedWidth
    fun expandedDevice_verifyUsingPermanenetNavigationRail(){
        compostTestRule.setContent { 
            ReplyApp(windowSize = WindowWidthSizeClass.Expanded)
        }
        compostTestRule.onNodeWithTag(
            compostTestRule.activity.getString(R.string.navigation_drawer)
        ).assertExists()
    }
}