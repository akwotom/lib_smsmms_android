package com.afkanerd.smswithoutborders_libsmsmms

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.afkanerd.smswithoutborders_libsmsmms.ui.components.FailedMessageOptionsModal
import com.afkanerd.smswithoutborders_libsmsmms.ui.components.SearchCounterCompose
import com.afkanerd.smswithoutborders_libsmsmms.ui.components.SearchTopAppBarText
import com.afkanerd.smswithoutborders_libsmsmms.ui.components.ShortCodeAlert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConversationsComponentsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchCounter_displaysIndexAndTotal() {
        composeTestRule.setContent {
            SearchCounterCompose(index = "3", total = "10")
        }
        composeTestRule
            .onNodeWithText("3/10", substring = true)
            .assertIsDisplayed()
    }


    @Test
    fun searchTopAppBar_displaysPlaceholder_whenEmpty() {
        composeTestRule.setContent {
            SearchTopAppBarText(searchQuery = "")
        }
        composeTestRule
            .onNodeWithText("Text message", substring = true)
            .assertIsDisplayed()
    }


    @Test
    fun searchTopAppBar_closeButton_isDisplayed() {
        composeTestRule.setContent {
            SearchTopAppBarText(searchQuery = "hello")
        }
        composeTestRule
            .onNodeWithContentDescription("cancel search", ignoreCase = true)
            .assertIsDisplayed()
    }

    @Test
    fun failedMessageModal_resendOption_isDisplayed() {
        composeTestRule.setContent {
            FailedMessageOptionsModal(
                retryCallback = {},
                deleteCallback = {},
                dismissCallback = {}
            )
        }
        composeTestRule
            .onNodeWithText("Resend message", substring = true, ignoreCase = true)
            .assertIsDisplayed()
    }

    @Test
    fun failedMessageModal_deleteOption_isDisplayed() {
        composeTestRule.setContent {
            FailedMessageOptionsModal(
                retryCallback = {},
                deleteCallback = {},
                dismissCallback = {}
            )
        }
        composeTestRule
            .onNodeWithText("Delete", substring = true, ignoreCase = true)
            .assertIsDisplayed()
    }

    @Test
    fun shortCodeAlert_dismissButton_isDisplayed() {
        composeTestRule.setContent {
            ShortCodeAlert(
                dismissCallback = {}
            )
        }
        composeTestRule
            .onNodeWithText("OK", substring = true, ignoreCase = true)
            .assertIsDisplayed()
    }
}