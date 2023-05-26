package com.project.mindmate.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.mindmate.Models.PostModel
import com.project.mindmate.R

class PostAdapter(private val postList : ArrayList<PostModel>)
    : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private lateinit var listener: onItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_items, parent,false)
        return PostViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentPost = postList[position]
        holder.postText.text = currentPost.postText
    }

    class PostViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val postText : TextView = itemView.findViewById(R.id.postTv)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        listener = clickListener
    }

}