package com.dheeraj.hilt.daggerhilt.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dheeraj.hilt.daggerhilt.R
import com.dheeraj.hilt.daggerhilt.model.Blog
import kotlinx.android.synthetic.main.item_blog.view.*

class BlogAdapter(private val blogs: ArrayList<Blog>) :
    RecyclerView.Adapter<BlogAdapter.BlogViewHolder>() {

    class BlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(blog: Blog) {
            itemView.tv_blog.text = blog.title
            itemView.tv_blog.setOnClickListener {
                Toast.makeText(itemView.context, blog.title, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_blog, parent, false)
        return BlogViewHolder(view)
    }

    override fun getItemCount(): Int = blogs.size

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        holder.bindView(blogs[position])
    }

    fun setBlogs(blogs: ArrayList<Blog>) {
        this.blogs.apply {
            clear()
            addAll(blogs)
        }
    }


}