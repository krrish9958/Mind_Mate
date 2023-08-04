package com.project.mindmate.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.project.mindmate.Models.MoodLogsModel
import com.project.mindmate.R

class MoodLogsAdapter(private val moodsList : ArrayList<MoodLogsModel>) :
        RecyclerView.Adapter<MoodLogsAdapter.MyViewHolder>(){

    private var selectedItemPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.mood_logs_item,parent, false)
        return MyViewHolder(itemView, selectedItemPosition, this)
    }

    override fun getItemCount(): Int {
        return moodsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMood = moodsList[position]
        holder.imgMood.setImageResource(currentMood.imgMood)
        holder.textMood.text = currentMood.textMood

        val isSelected = position == selectedItemPosition

        // Update the appearance of the mood card based on its selected state
        if (isSelected) {
            holder.moodCard.isChecked = true
            holder.moodCard.strokeWidth = 6
            holder.moodCard.strokeColor = ContextCompat.getColor(holder.itemView.context, R.color.purple_theme)
            holder.moodCard.cardElevation = holder.checkedElevation // Set the elevation for the checked state
            // Set any other properties you want for the checked state
        } else {
            holder.moodCard.isChecked = false
            holder.moodCard.strokeWidth = 0 // Set the stroke width for the unchecked state
            // Set any other properties you want for the unchecked state
        }
    }




    class MyViewHolder(
        itemView: View,
        private var selectedItemPosition: Int,
        private val adapter: MoodLogsAdapter
        ) : RecyclerView.ViewHolder(itemView) {
        val imgMood: ImageView = itemView.findViewById(R.id.imgMood)
        val textMood: TextView = itemView.findViewById(R.id.textMood)
        val moodCard: MaterialCardView = itemView.findViewById(R.id.moodCard)
        val checkedElevation = 8f // Elevation value for checked state

        init {
            moodCard.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                   adapter.setSelectedItem(position)
                }
            }
        }
    }

    fun setSelectedItem(position: Int) {
        val previousSelectedItemPosition = selectedItemPosition
        selectedItemPosition = position
        notifyItemChanged(previousSelectedItemPosition)
        notifyItemChanged(selectedItemPosition)
    }

}