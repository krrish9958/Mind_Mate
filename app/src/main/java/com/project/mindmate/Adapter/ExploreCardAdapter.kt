package com.project.mindmate.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.mindmate.Models.ExploreCardModel
import com.project.mindmate.R

class ExploreCardAdapter(private val cardList : ArrayList<ExploreCardModel>): RecyclerView.Adapter<ExploreCardAdapter.MyViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.list_item_explorecard,parent,false)
            return MyViewHolder(itemView)
  }

  override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
   val currentCard = cardList[position]
      holder.exploreCardImg.setImageResource(currentCard.cardImg)
      holder.exploreCardHeading.text=currentCard.heading
  }

  override fun getItemCount(): Int {
    return cardList.size
  }

  class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
   val exploreCardImg : ImageView = itemView.findViewById(R.id.exploreCardImg)
   val exploreCardHeading : TextView = itemView.findViewById(R.id.exploreCardHeading)
  }


 }