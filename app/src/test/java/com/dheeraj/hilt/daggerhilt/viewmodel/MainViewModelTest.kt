package com.dheeraj.hilt.daggerhilt.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dheeraj.hilt.daggerhilt.model.Blog
import com.dheeraj.hilt.daggerhilt.network.BlogRepo
import com.dheeraj.hilt.daggerhilt.util.Resource
import com.dheeraj.hilt.daggerhilt.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    // region Helper Fields

    @Mock private lateinit var blogRepo: BlogRepo

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
            mainViewModel.getBlogs(true).observeForever(blogApiObserver)
            verify(blogRepo, times(1)).getBlogs(true)
            verify(blogApiObserver).onChanged(Resource.success(data = emptyList()))
            mainViewModel.getBlogs(true).removeObserver(blogApiObserver)
        }
    }

    @Test
    fun getBlogs_ErrorFromServer_errorReturned() {
        testCoroutineRule.runBlockingTest {
            failure()
            mainViewModel.getBlogs(true).observeForever(blogApiObserver)
            verify(blogRepo, times(1)).getBlogs(true)
            verify(blogApiObserver).onChanged(Resource.error(data = null, message = "Error"))
            mainViewModel.getBlogs(true).removeObserver(blogApiObserver)
        }
    }

    // region Helper Methods

    private suspend fun success() {
        doReturn(emptyList<Blog>()).`when`(blogRepo).getBlogs(true)
    }

    private suspend fun failure() {
        doThrow(RuntimeException("Error")).`when`(blogRepo).getBlogs(true)
    }

    // endregion Helper Methods

}