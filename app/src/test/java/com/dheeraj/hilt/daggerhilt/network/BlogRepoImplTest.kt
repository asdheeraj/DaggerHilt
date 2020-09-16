package com.dheeraj.hilt.daggerhilt.network

import android.accounts.NetworkErrorException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dheeraj.hilt.daggerhilt.cache.BlogDao
import com.dheeraj.hilt.daggerhilt.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Assert.*
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
class BlogRepoImplTest {

    private lateinit var blogRepo: BlogRepoImpl

    @Mock private lateinit var blogApi: BlogApi

    @Mock private lateinit var blogDao: BlogDao

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        blogRepo = BlogRepoImpl(blogApi, blogDao)
    }

    @Test
     fun getBlogs_success_200returned() {
        testCoroutineRule.runBlockingTest {
            `when`(blogDao.getBlogs()).thenReturn(emptyList())
             blogRepo.getBlogs(true)
             verify(blogApi, times(1)).getBlogs()
        }
    }

    @Test
    fun getBlogs_failure_exceptionThrown() {
        testCoroutineRule.runBlockingTest {
            `when`(blogDao.getBlogs()).thenReturn(emptyList())
            try {
                blogRepo.getBlogs(false)
            } catch (e: Exception) {
                assertTrue(e is NetworkErrorException)
            }
        }
    }
}
