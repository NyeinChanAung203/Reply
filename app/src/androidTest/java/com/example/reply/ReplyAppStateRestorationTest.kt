package com.example.reply

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.reply.data.local.LocalEmailsDataProvider
import com.example.reply.ui.ReplyApp
import org.junit.Rule
import org.junit.Test

class ReplyAppStateRestorationTest {

    /**
     * Note: To access to an empty activity, the code uses ComponentActivity instead of
     * MainActivity.
     */
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    @TestCompactWidth
    fun compactDevice_selectedEmailEmailRetained_afterChanged(){
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent {
            ReplyApp(windowSize = WindowWidthSizeClass.Compact)
        }

        // third email is display
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body)
        ).assertIsDisplayed()

        // Opened detail page
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].subject)
        ).performClick()

        // show back button and body of email
        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.getString(R.string.navigation_back)
        ).assertExists()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body)
        ).assertExists()

        // Simulate configuration change
        stateRestorationTester.emulateSavedInstanceStateRestore()

        //
        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.getString(R.string.navigation_back)
        ).assertExists()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body)
        ).assertExists()
    }

    @Test
    @TestExpandedWidth
    fun expandedDevice_selectedEmailEmailRetained_afterConfigChange() {
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent { 
            ReplyApp(windowSize = WindowWidthSizeClass.Expanded)
        }

        // third email is display
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body)
        ).assertIsDisplayed()

        // click third email
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].subject)
        ).performClick()

        // Verify that third email is displayed on the details screen
        composeTestRule.onNodeWithTag(
            composeTestRule.activity.getString(R.string.details_screen)
        ).onChildren().assertAny(
            hasAnyDescendant(
                hasText(composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body))
            )
        )

        stateRestorationTester.emulateSavedInstanceStateRestore()

        // Verify that third email is displayed on the details screen
        composeTestRule.onNodeWithTag(
            composeTestRule.activity.getString(R.string.details_screen)
        ).onChildren().assertAny(
            hasAnyDescendant(
                hasText(composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body))
            )
        )
    }
}