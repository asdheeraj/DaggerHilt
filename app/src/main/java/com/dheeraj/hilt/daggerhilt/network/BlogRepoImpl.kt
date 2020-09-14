package com.dheeraj.hilt.daggerhilt.network

import android.accounts.NetworkErrorException
import com.dheeraj.hilt.daggerhilt.cache.BlogDao
import com.dheeraj.hilt.daggerhilt.model.Blog
import javax.inject.Inject

class BlogRepoImpl
@Inject constructor(
    private val blogApi: BlogApi,
    private val blogDao: BlogDao
) : BlogRepo {
    override suspend fun getBlogs(isInternetAvailable: Boolean): List<Blog> {
        if (blogDao.getBlogs().isEmpty()) {
            if (isInternetAvailable) blogDao.insertAll(blogApi.getBlogs()) else throw NetworkErrorException()
        }
        return blogDao.getBlogs()
    }
}