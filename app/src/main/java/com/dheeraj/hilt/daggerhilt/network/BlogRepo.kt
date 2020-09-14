package com.dheeraj.hilt.daggerhilt.network

import com.dheeraj.hilt.daggerhilt.model.Blog

interface BlogRepo {

    suspend fun getBlogs(isInternetAvailable: Boolean): List<Blog>
}