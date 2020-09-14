package com.dheeraj.hilt.daggerhilt.di

import com.dheeraj.hilt.daggerhilt.cache.BlogDao
import com.dheeraj.hilt.daggerhilt.network.BlogApi
import com.dheeraj.hilt.daggerhilt.network.BlogRepo
import com.dheeraj.hilt.daggerhilt.network.BlogRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideBlogRepo(blogApi: BlogApi, blogDao: BlogDao): BlogRepo =
        BlogRepoImpl(blogApi, blogDao)
}