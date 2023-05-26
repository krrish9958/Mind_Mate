package com.project.mindmate.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.mindmate.Models.MoodLogsModel
import com.project.mindmate.R

class MoodLogsAdapter(private val moodsList : ArrayList<MoodLogsModel>) :
        RecyclerView.Adapter<MoodLogsAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.mood_logs_item,parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return moodsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMood = moodsList[position]
        holder.imgMood.setImageResource(currentMood.imgMood)
        holder.textMood.text = currentMood.textMood
    }

    class MyViewHolder(val itemView : View) : RecyclerView.ViewHolder(itemView) {
        val imgMood : ImageView = itemView.findViewById(R.id.imgMood)
        val textMood : TextView = itemView.findViewById(R.id.textMood)
    }
}