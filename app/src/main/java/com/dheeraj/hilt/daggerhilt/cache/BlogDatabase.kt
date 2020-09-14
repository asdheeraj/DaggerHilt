package com.dheeraj.hilt.daggerhilt.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dheeraj.hilt.daggerhilt.model.Blog

@Database(entities = [Blog::class], version = 1)
abstract class BlogDatabase: RoomDatabase() {

    abstract fun blogDao(): BlogDao

    companion object {
        const val DATABASE_NAME = "blog_db"
    }

}