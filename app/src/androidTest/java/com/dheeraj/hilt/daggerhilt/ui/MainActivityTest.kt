package com.dheeraj.hilt.daggerhilt.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dheeraj.hilt.daggerhilt.R
import com.dheeraj.hilt.daggerhilt.model.Blog
import com.dheeraj.hilt.daggerhilt.ui.adapter.BlogAdapter
import com.dheeraj.hilt.daggerhilt.util.launchFragmentInHiltContainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: MainFragmentFactory

    @Before
    fun init() {
        hiltRule.inject()
    }



    @Test
    fun test_activity_inView() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @Test
    fun test_fragment_launch() {
        launchFragmentInHiltContainer<MainFragment>(
            factory = fragmentFactory
        )
    }

    @Test
    fun test_recyclerView_visible_onLaunch() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.rv_blogs)).check(matches(isDisplayed()))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun rv_loaded() {
        val fragmentScenario = launchFragmentInHiltContainer<MainFragment>(
            factory = fragmentFactory
        )
        onView(withId(R.id.rv_blogs)).perform(actionOnItemAtPosition<BlogAdapter.BlogViewHolder>(3, click()))
    }

}