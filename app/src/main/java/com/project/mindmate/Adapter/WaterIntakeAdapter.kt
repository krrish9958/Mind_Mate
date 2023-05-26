package com.project.mindmate.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.project.mindmate.Models.WaterIntakeModel
import com.project.mindmate.R

class WaterIntakeAdapter(private val waterList : ArrayList<WaterIntakeModel>) :
        RecyclerView.Adapter<WaterIntakeAdapter.MyViewHolder>(){

    private lateinit var listener: WaterIntakeAdapter.onItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.water_intake_logs, parent, false)
        return MyViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int {
        return waterList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = waterList[position]
        holder.image.setImageResource(currentItem.image)
    }

    class MyViewHolder(itemView : View, clickListener: WaterIntakeAdapter.onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val image : ImageView = itemView.findViewById(R.id.waterImg)
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