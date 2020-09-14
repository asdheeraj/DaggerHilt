package com.dheeraj.hilt.daggerhilt


import androidx.test.core.app.launchActivity
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.dheeraj.hilt.daggerhilt.di.AppModule
import com.dheeraj.hilt.daggerhilt.ui.MainActivity
import com.dheeraj.hilt.daggerhilt.ui.MainFragment
import com.dheeraj.hilt.daggerhilt.ui.MainFragmentFactory
import com.dheeraj.hilt.daggerhilt.util.launchFragmentInHiltContainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@UninstallModules(AppModule::class)
@HiltAndroidTest
class MainTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var someString: String

    @Inject
    lateinit var fragmentFactory: MainFragmentFactory

//    @BindValue var myString: String = "gggf" // Doesn't work?? I'm prob doing it wrong.

    @Before
    fun init() {
        hiltRule.inject()


    }

    @Test
    fun hiltTest(){
        assertThat(someString, containsString("TEST string"))
    }

    @Test
    fun mainFragmentTest(){
        val scenario = launchFragmentInHiltContainer<MainFragment> (
            factory = fragmentFactory
        ) {

        }
    }

    @Test
    fun mainActivityTest(){
        val scenario = launchActivity<MainActivity>()
    }

    @Module
    @InstallIn(ApplicationComponent::class)
    object ProductionModule {


        @Singleton
        @Provides
        fun provideString(): String{
            return "This is a TEST string I'm providing for injection"
        }
    }
}