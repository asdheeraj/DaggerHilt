package com.dheeraj.hilt.daggerhilt.network

import com.dheeraj.hilt.daggerhilt.cache.BlogDao
import com.dheeraj.hilt.daggerhilt.model.Blog
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class BlogRepoImpl
@Inject constructor(
    private val blogApi: BlogApi,
    private val blogDao: BlogDao
) : BlogRepo {
    override suspend fun getBlogs(isInternetAvailable: Boolean): List<Blog> {
        if (blogDao.getBlogs().isEmpty()) {
            if (isInternetAvailable) blogDao.insertAll(blogApi.getBlogs()) else throw CancellationException("No Network Detected")
        }
        return blogDao.getBlogs()
    }
}