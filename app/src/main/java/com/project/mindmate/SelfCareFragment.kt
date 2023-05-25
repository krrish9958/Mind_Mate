package com.project.mindmate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.mindmate.Adapter.ExploreCardAdapter
import com.project.mindmate.Models.ExploreCardModel

class SelfCareFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : ExploreCardAdapter
    private lateinit var cardList : ArrayList<ExploreCardModel>
    private lateinit var cardHeading : Array<String>
    private lateinit var cardImg : Array<Int>
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
        return view
    }

    private fun getCardData() {
        for (i in cardImg.indices){
            val card = ExploreCardModel(cardImg[i],cardHeading[i])
            cardList.add(card)
        }
        recyclerView.adapter=adapter
    }
}