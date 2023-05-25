package com.project.mindmate

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.project.mindmate.Adapter.InputStatsAdapter
import com.project.mindmate.Models.InputStatsModel

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : InputStatsAdapter
    private lateinit var inputList : ArrayList<InputStatsModel>
    private lateinit var inputIcon : Array<Int>
    private lateinit var inputType : Array<String>
    private lateinit var input : Array<String>

    private lateinit var addLogsCard : MaterialCardView
    private lateinit var addLogsBtn : FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        addLogsBtn = view.findViewById(R.id.addLogsBtn)
        addLogsCard = view.findViewById(R.id.addLogsCard)
        recyclerView = view.findViewById(R.id.inputStatsRecyclerView)
        inputList = arrayListOf<InputStatsModel>()
        adapter = InputStatsAdapter(inputList)
        inputDataInitialize()
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)


        addLogsCard.setOnClickListener {
            startActivity(Intent(requireContext(), TrackingStatsActivity::class.java))
        }
        addLogsBtn.setOnClickListener {
            startActivity(Intent(requireContext(), TrackingStatsActivity::class.java))
        }
        return view
    }

    private fun inputDataInitialize() {
        inputIcon = arrayOf(
            R.drawable.mood,
            R.drawable.sleep_score,
            R.drawable.water_medium
        )

        inputType = arrayOf(
            "Mood",
            "Sleep",
            "Water"
        )

        input = arrayOf(
            "Happy",
            "Great",
            "8 glasses"
        )
        getInputData()
    }

    private fun getInputData() {
        for (i in inputIcon.indices){
            val data = InputStatsModel(inputIcon[i], inputType[i], input[i])
            inputList.add(data)
            recyclerView.adapter = adapter
        }
    }

}