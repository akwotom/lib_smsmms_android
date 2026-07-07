package com.afkanerd.smswithoutborders_libsmsmms

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.afkanerd.smswithoutborders_libsmsmms.extensions.context.makeE16PhoneNumber
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhoneNumberInstrumentationTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun formatsInternationalNumber() {
        val result = context.makeE16PhoneNumber("+237671234567")
        assertEquals("+237671234567", result)
    }

    @Test
    fun removesSpacesAndDashes() {
        val result = context.makeE16PhoneNumber("+237 671-234-567")
        assertEquals("+237671234567", result)
    }
}