package com.dheeraj.hilt.daggerhilt.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.dheeraj.hilt.daggerhilt.R
import com.dheeraj.hilt.daggerhilt.util.Status
import com.dheeraj.hilt.daggerhilt.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*



@AndroidEntryPoint
class MainFragment
constructor(
    private val someString: String
) : Fragment(R.layout.fragment_main) {

    private val TAG: String = "AppDebug"

    private val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG,  "Constructor Injection Working! $someString")
        initObservers()
    }

    private fun initObservers() {
        mainViewModel.getBlogs(isInternetAvailable(activity)).observe(viewLifecycleOwner, Observer { resource ->
            val stringBuilder = StringBuilder()
            when (resource.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    resource.data?.forEach { blog ->
                        stringBuilder.append(blog.title + "\n")
                    }
                    textView.text = stringBuilder.toString()
                }

                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }

                Status.ERROR -> {
                    Toast.makeText(activity, resource.message, Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                }
            }

        })
    }

    private fun isInternetAvailable(context: Context?): Boolean {
        var result = false
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }
        return result
    }

}