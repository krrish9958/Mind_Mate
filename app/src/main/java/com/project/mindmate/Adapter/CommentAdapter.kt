package com.project.mindmate.Adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.project.mindmate.Models.CommentModel
import com.project.mindmate.R

class CommentAdapter(private val commentList: ArrayList<CommentModel>)
    : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private lateinit var dbReference: DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.comments_list, parent, false)
        return CommentViewHolder(itemview)
    }

    override fun getItemCount(): Int {
       return commentList.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val currentComment = commentList[position]
        holder.commentText.text = currentComment.commentText
    }


    class CommentViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        val commentText : TextView = itemview.findViewById(R.id.commentsTv)
    }
}