package com.project.mindmate.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.project.mindmate.Models.SleepLogsModel
import com.project.mindmate.R

class SleepLogsAdapter(private val sleepList: ArrayList<SleepLogsModel>) :
    RecyclerView.Adapter<SleepLogsAdapter.MyViewHolder>() {

    private var selectedItemPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.sleep_logs_item, parent, false)
        return MyViewHolder(itemView, selectedItemPosition, this)
    }

    override fun getItemCount(): Int {
        return sleepList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = sleepList[position]
        holder.sleepImg.setImageResource(currentItem.imgSleep)
        holder.sleepText.text = currentItem.textSleep


        val isSelected = position == selectedItemPosition

        // Update the appearance of the mood card based on its selected state
        if (isSelected) {
            holder.sleepCard.isChecked = true
            holder.sleepCard.strokeWidth = 6
            holder.sleepCard.strokeColor = ContextCompat.getColor(holder.itemView.context, R.color.purple_theme)
            holder.sleepCard.cardElevation = holder.checkedElevation // Set the elevation for the checked state
            // Set any other properties you want for the checked state
        } else {
            holder.sleepCard.isChecked = false
            holder.sleepCard.strokeWidth = 0 // Set the stroke width for the unchecked state
            // Set any other properties you want for the unchecked state
        }
    }

    class MyViewHolder(
        itemView: View,
        private var selectedItemPosition: Int,
        private val adapter: SleepLogsAdapter,
    ) : RecyclerView.ViewHolder(itemView) {
        val sleepImg: ImageView = itemView.findViewById(R.id.imgSleep)
        val sleepText: TextView = itemView.findViewById(R.id.textSleep)
        val sleepCard: MaterialCardView = itemView.findViewById(R.id.sleepCard)
        val checkedElevation = 8f // Elevation value for checked state

        init {
            sleepCard.setOnClickListener {
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

    fun getSelectedSleepPosition(): Int {
        return selectedItemPosition
    }
}