package com.dheeraj.hilt.daggerhilt.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.dheeraj.hilt.daggerhilt.R
import com.dheeraj.hilt.daggerhilt.model.Blog
import kotlinx.android.synthetic.main.item_blog.view.*

class BlogListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Blog>() {

        override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean
                = oldItem.pk == newItem.pk

        override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean
                = oldItem == newItem

    }
    private val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return BlogListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_blog,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BlogListViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Blog>) {
        differ.submitList(list)
    }

    class BlogListViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Blog) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.tv_blog.text = item.title
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Blog)
    }
}