package com.dheeraj.hilt.daggerhilt.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.dheeraj.hilt.daggerhilt.model.Blog
import com.dheeraj.hilt.daggerhilt.network.BlogRepo
import com.dheeraj.hilt.daggerhilt.util.Resource
import com.dheeraj.hilt.daggerhilt.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.*
import java.lang.RuntimeException


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    // region constants

    // endregion constants

    // region Helper Fields

    @Mock private lateinit var blogRepo: BlogRepo

   // @Mock private lateinit var savedStateHandle: SavedStateHandle

    @Mock private lateinit var blogApiObserver: Observer<Resource<List<Blog>>>

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    // endregion Helper Fields

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(blogRepo)
    }

    @Test
    fun getBlogs_200ReturnedFromServer_successReturned() {
        testCoroutineRule.runBlockingTest {
            success()
            mainViewModel.getBlogs().observeForever(blogApiObserver)
            verify(blogRepo, times(1)).getBlogs()
            verify(blogApiObserver).onChanged(Resource.success(data = emptyList()))
            mainViewModel.getBlogs().removeObserver(blogApiObserver)
        }
    }

    @Test
    fun getBlogs_ErrorFromServer_errorReturned() {
        testCoroutineRule.runBlockingTest {
            failure()
            mainViewModel.getBlogs().observeForever(blogApiObserver)
            verify(blogRepo, times(1)).getBlogs()
            verify(blogApiObserver).onChanged(Resource.error(data = null, message = "Error"))
            mainViewModel.getBlogs().removeObserver(blogApiObserver)
        }
    }

    // region Helper Methods

    private suspend fun success() {
        doReturn(emptyList<Blog>()).`when`(blogRepo).getBlogs()
    }

    private suspend fun failure() {
        doThrow(RuntimeException("Error")).`when`(blogRepo).getBlogs()
    }

    // endregion Helper Methods

    // region Helper classes

    // endregion Helper classes
}