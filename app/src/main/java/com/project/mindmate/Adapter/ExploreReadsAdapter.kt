package com.project.mindmate.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.mindmate.Models.ExploreReadsModel
import com.project.mindmate.R

class ExploreReadsAdapter(var readList: ArrayList<ExploreReadsModel>):RecyclerView.Adapter<ExploreReadsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.listiitem_explorereads,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentRead = readList[position]
        holder.heading.text = currentRead.heading
        holder.para.text=currentRead.para
        holder.cardReadsImg.setImageResource(currentRead.Img)
    }

    override fun getItemCount(): Int {
      return readList.size
    }

    class MyViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        var heading : TextView = itemView.findViewById(R.id.heading)
        var para : TextView = itemView.findViewById(R.id.para)
        var cardReadsImg :ImageView = itemView.findViewById(R.id.cardReadImg)
    }

}