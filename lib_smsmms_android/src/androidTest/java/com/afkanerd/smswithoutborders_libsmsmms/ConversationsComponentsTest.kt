package com.afkanerd.smswithoutborders_libsmsmms

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.afkanerd.smswithoutborders_libsmsmms.ui.components.ChatCompose
import com.afkanerd.smswithoutborders_libsmsmms.ui.components.FailedMessageOptionsModal
import com.afkanerd.smswithoutborders_libsmsmms.ui.components.SearchCounterCompose
import com.afkanerd.smswithoutborders_libsmsmms.ui.components.SearchTopAppBarText
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
    fun chatCompose_plusButton_isDisplayed() {
        composeTestRule.setContent {
            ChatCompose(
                value = "",
                subscriptionId = -1L,
                sendMmsCallback = {},
                smsSendCallback = {}
            )
        }
        composeTestRule
            .onNodeWithContentDescription("Attachment Options")
            .assertIsDisplayed()
    }

    @Test
    fun chatCompose_plusButton_click_opensBottomSheet() {
        composeTestRule.setContent {
            ChatCompose(
                value = "",
                subscriptionId = -1L,
                sendMmsCallback = {},
                smsSendCallback = {}
            )
        }

        composeTestRule
            .onNodeWithContentDescription("Attachment Options")
            .performClick()

        composeTestRule
            .onNodeWithText("Photo")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Contact")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("File")
            .assertIsDisplayed()
    }

    @Test
    fun chatCompose_bottomSheet_photoOption_isDisplayed() {
        composeTestRule.setContent {
            ChatCompose(
                value = "",
                subscriptionId = -1L,
                sendMmsCallback = {},
                smsSendCallback = {}
            )
        }
        composeTestRule
            .onNodeWithContentDescription("Attachment Options")
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Photo")
            .assertIsDisplayed()
    }

    @Test
    fun chatCompose_bottomSheet_contactOption_isDisplayed() {
        composeTestRule.setContent {
            ChatCompose(
                value = "",
                subscriptionId = -1L,
                sendMmsCallback = {},
                smsSendCallback = {}
            )
        }
        composeTestRule
            .onNodeWithContentDescription("Attachment Options")
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Attach Contact")
            .assertIsDisplayed()
    }

    @Test
    fun chatCompose_bottomSheet_fileOption_isDisplayed() {
        composeTestRule.setContent {
            ChatCompose(
                value = "",
                subscriptionId = -1L,
                sendMmsCallback = {},
                smsSendCallback = {}
            )
        }
        composeTestRule
            .onNodeWithContentDescription("Attachment Options")
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Attach File")
            .assertIsDisplayed()
    }

    @Test
    fun chatCompose_sendButton_hiddenWhenEmpty() {
        composeTestRule.setContent {
            ChatCompose(
                value = "",
                subscriptionId = -1L,
                sendMmsCallback = {},
                smsSendCallback = {}
            )
        }
        composeTestRule
            .onNodeWithContentDescription("send message", ignoreCase = true)
            .assertDoesNotExist()
    }

    @Test
    fun chatCompose_sendButton_appearsWhenTextEntered() {
        composeTestRule.setContent {
            ChatCompose(
                value = "Hello",
                subscriptionId = -1L,
                sendMmsCallback = {},
                smsSendCallback = {}
            )
        }
        composeTestRule
            .onNodeWithContentDescription("send message", ignoreCase = true)
            .assertIsDisplayed()
    }

    @Test
    fun chatCompose_sendButton_click_triggersSmsCallback() {
        var smsSent = false
        composeTestRule.setContent {
            ChatCompose(
                value = "Hello",
                subscriptionId = -1L,
                sendMmsCallback = {},
                smsSendCallback = { smsSent = true }
            )
        }
        composeTestRule
            .onNodeWithContentDescription("send message", ignoreCase = true)
            .performClick()

        assert(smsSent) { "SMS send callback was not triggered" }
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
}