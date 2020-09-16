package com.dheeraj.hilt.daggerhilt.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dheeraj.hilt.daggerhilt.network.BlogRepo
import com.dheeraj.hilt.daggerhilt.util.Resource

class MainViewModel
@ViewModelInject
constructor(
    private val blogRepo: BlogRepo
) : ViewModel() {

    fun getBlogs(isInternetAvailable: Boolean) = liveData {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = blogRepo.getBlogs(isInternetAvailable)))
        } catch (e: Exception) {
            emit((Resource.error(data = null, message = e.message ?: "Unknown Exception")))

        }
    }
}