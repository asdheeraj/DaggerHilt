package com.dheeraj.hilt.daggerhilt.network

import com.dheeraj.hilt.daggerhilt.model.Blog
import retrofit2.http.GET

interface BlogApi {

    @GET("blogs")
    suspend fun getBlogs() : List<Blog>
}