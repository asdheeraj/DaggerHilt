package com.dheeraj.hilt.daggerhilt.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dheeraj.hilt.daggerhilt.model.Blog

@Dao
interface BlogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(blogs: List<Blog>)

    @Query("SELECT * FROM blogs")
    suspend fun getBlogs(): List<Blog>
}