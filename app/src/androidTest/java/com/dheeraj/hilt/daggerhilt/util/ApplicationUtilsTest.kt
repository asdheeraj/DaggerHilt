package com.dheeraj.hilt.daggerhilt.util

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class ApplicationUtilsTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var applicationUtils: ApplicationUtils

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun test_isInternetAvailable() {
        //This function changes to true or false based on the device internet connection availability
        assertTrue(applicationUtils.isInternetAvailable())
    }
}
