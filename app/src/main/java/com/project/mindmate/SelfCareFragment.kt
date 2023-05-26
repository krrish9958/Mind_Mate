package com.project.mindmate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.mindmate.Adapter.ExploreCardAdapter
import com.project.mindmate.Adapter.ExploreReadsAdapter
import com.project.mindmate.Models.ExploreCardModel
import com.project.mindmate.Models.ExploreReadsModel

class SelfCareFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : ExploreCardAdapter
    private lateinit var cardList : ArrayList<ExploreCardModel>
    private lateinit var cardHeading : Array<String>
    private lateinit var cardImg : Array<Int>



    //recycler read variabes
    private lateinit var recyclerViewReads: RecyclerView
    private lateinit var adapterReads : ExploreReadsAdapter
    private lateinit var readList : ArrayList<ExploreReadsModel>
    private lateinit var readHeading : Array<String>
    private lateinit var readPara : Array<String>
    private lateinit var readImg : Array<Int>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_self_care, container, false)
        recyclerView=view.findViewById(R.id.selfCareRecyclerView)
        cardList= arrayListOf<ExploreCardModel>()
        adapter= ExploreCardAdapter(cardList)
        cardHeading = arrayOf(
            "Exercise is for the quality of life ",
            "Sports boost your overall health and offer other benefits",
            "Music is therapy & relaxes Mind",
        "Meditation can produce a deep state of relaxation and a tranquil mind"
        )
        cardImg= arrayOf(
        R.drawable.excercise,
            R.drawable.sports,
            R.drawable.music,
            R.drawable.meditation
        )
        recyclerView.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        recyclerView.setHasFixedSize(true)
        getCardData()







        //recycler read code
        recyclerViewReads = view.findViewById(R.id.recyclerViewReads)
        readList= arrayListOf<ExploreReadsModel>()
        adapterReads = ExploreReadsAdapter(readList)

        readHeading = arrayOf(
            " Don’t skimp on sleep!😴",
            "Talk to someone",
            "Healthy diet to support strong mental health",
            "Spend time with animals"
        )
        readPara = arrayOf(  "It matters more than you think. Sleep is our body and mind's best way to recharge and rejuvenate. One way to get sleep better is to take a break from the stimulation of screens ",
            "Talk to a friendly face. If you have concerns, stresses or worries, sharing these with someone who cares is one of the most effective ways to calm your nervous system and relieve stress.",
            "Foods that can support your mood include fatty fish rich in omega-3s, nuts (walnuts, almonds, cashews and peanuts), avocados, beans, leafy greens ",
            "Lots of people find that being with animals is calming and enjoyable. You could try pet-sitting or dog walking, feed birds from your window, or visit a local community farm.")
        readImg = arrayOf(
            R.drawable.sleep,
            R.drawable.talk,
            R.drawable.food,
            R.drawable.pet
        )
        recyclerViewReads.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        recyclerViewReads.setHasFixedSize(true)

        getReadData()
        return view




    }

    private fun getReadData() {
        for (i in readImg.indices){
            val read = ExploreReadsModel(readHeading[i],readPara[i],readImg[i])
            readList.add(read)
        }
        recyclerViewReads.adapter=adapterReads
    }

    private fun getCardData() {
        for (i in cardImg.indices){
            val card = ExploreCardModel(cardImg[i],cardHeading[i])
            cardList.add(card)
        }
        recyclerView.adapter=adapter
    }
}