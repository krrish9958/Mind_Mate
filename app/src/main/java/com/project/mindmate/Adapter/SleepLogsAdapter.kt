package com.project.mindmate.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.mindmate.Models.SleepLogsModel
import com.project.mindmate.R

class SleepLogsAdapter(private val sleepList : ArrayList<SleepLogsModel>):
        RecyclerView.Adapter<SleepLogsAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.sleep_logs_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return sleepList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = sleepList[position]
        holder.sleepImg.setImageResource(currentItem.imgSleep)
        holder.sleepText.text = currentItem.textSleep
    }

    class MyViewHolder(val itemView : View) : RecyclerView.ViewHolder(itemView) {
       val sleepImg : ImageView = itemView.findViewById(R.id.imgSleep)
       val sleepText : TextView = itemView.findViewById(R.id.textSleep)
    }
}