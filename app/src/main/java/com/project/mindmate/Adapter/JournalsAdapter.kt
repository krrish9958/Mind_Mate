package com.project.mindmate.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.project.mindmate.Models.Journal
import com.project.mindmate.R

class JournalsAdapter(private val context: Context, val listener: JournalsClickListener) :
    RecyclerView.Adapter<JournalsAdapter.JournalViewHolder>() {

    private val JournalsList = ArrayList<Journal>()
    private val fullList = ArrayList<Journal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        return JournalViewHolder(
            LayoutInflater.from(context).inflate(R.layout.journals_list_items, parent, false)
        )
    }

    override fun getItemCount(): Int {
       return JournalsList.size
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        val currentJournal = JournalsList[position]
        holder.title.text = currentJournal.title
        holder.title.isSelected = true

        holder.journal.text = currentJournal.journal

        holder.date.text = currentJournal.date
        holder.date.isSelected = true

        holder.journals_layout.setOnClickListener {
            listener.onItemClicked(JournalsList[holder.adapterPosition])
        }

        holder.journals_layout.setOnLongClickListener {
            listener.onLongItemClicked(JournalsList[holder.adapterPosition], holder.journals_layout)
            true
        }
    }

    fun updateList(newList : List<Journal>){
        fullList.clear()
        fullList.addAll(newList)

        JournalsList.clear()
        JournalsList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun filterList(search : String){
        JournalsList.clear()
        for (item in fullList){
            if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                    item.journal?.lowercase()?.contains(search.lowercase()) == true){
                JournalsList.add(item)
            }
        }

        notifyDataSetChanged()
    }


    inner class JournalViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val journals_layout = itemView.findViewById<CardView>(R.id.cardLayoutJournals)
        val title = itemView.findViewById<TextView>(R.id.tvTitle)
        val journal = itemView.findViewById<TextView>(R.id.tvJournal)
        val date = itemView.findViewById<TextView>(R.id.tvDate)
    }

    interface JournalsClickListener{
        fun onItemClicked(journal: Journal)
        fun onLongItemClicked(journal: Journal, cardView: CardView)
    }

}