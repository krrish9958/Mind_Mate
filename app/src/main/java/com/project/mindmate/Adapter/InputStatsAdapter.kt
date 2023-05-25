package com.project.mindmate.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.mindmate.Models.InputStatsModel
import com.project.mindmate.R

class InputStatsAdapter(private val inputList : ArrayList<InputStatsModel>) :
        RecyclerView.Adapter<InputStatsAdapter.MyViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.daily_inputs_item, parent, false)
                return MyViewHolder(itemView)
        }

        override fun getItemCount(): Int {
               return inputList.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
                val currentInput = inputList[position]
                holder.inputIcon.setImageResource(currentInput.inputIcon)
                holder.inputType.text = currentInput.inputType
                holder.input.text = currentInput.input
        }

        class MyViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
                val inputIcon : ImageView = itemview.findViewById(R.id.inputStatsIcon)
                val inputType : TextView = itemview.findViewById(R.id.inputStatsType)
                val input : TextView = itemview.findViewById(R.id.inputStats)
        }
}