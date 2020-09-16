package com.dheeraj.hilt.daggerhilt.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dheeraj.hilt.daggerhilt.BuildConfig
import com.dheeraj.hilt.daggerhilt.R
import com.dheeraj.hilt.daggerhilt.model.Blog
import com.dheeraj.hilt.daggerhilt.ui.adapter.BlogAdapter
import com.dheeraj.hilt.daggerhilt.util.ApplicationUtils
import com.dheeraj.hilt.daggerhilt.util.Status
import com.dheeraj.hilt.daggerhilt.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment
constructor(
    private val someString: String
) : Fragment(R.layout.fragment_main) {

    private val TAG: String = "AppDebug"

    private val mainViewModel: MainViewModel by viewModels()

    private val blogAdapter: BlogAdapter = BlogAdapter(getInitialData())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "Constructor Injection Working! $someString")
        initViews()
        initObservers()
    }

    @Inject
    lateinit var applicationUtils: ApplicationUtils

    private fun initViews() {
        with(rv_blogs) {
            layoutManager = LinearLayoutManager(activity)
            adapter = blogAdapter
        }
    }

    private fun getInitialData(): ArrayList<Blog> = if(BuildConfig.DEBUG) mockBlogData() else arrayListOf()

    private fun initObservers() {
        mainViewModel.getBlogs(applicationUtils.isInternetAvailable())
            .observe(viewLifecycleOwner, Observer { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        setUpUI(resource.data as ArrayList<Blog>)
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

    private fun setUpUI(blogs: ArrayList<Blog>) {
        blogAdapter.apply {
            setBlogs(blogs)
            notifyDataSetChanged()
        }
    }

    private fun mockBlogData(): ArrayList<Blog> {
        return arrayListOf(
            Blog(1, "", "", "", "Title 1"),
            Blog(2, "", "", "", "Title 2"),
            Blog(3, "", "", "", "Title 3"),
            Blog(4, "", "", "", "Title 4"),
            Blog(5, "", "", "", "Title 5")
        )
    }

}