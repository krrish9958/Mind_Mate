package com.project.mindmate

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.project.mindmate.Adapter.InputStatsAdapter
import com.project.mindmate.Models.InputStatsModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

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

        val currentDate = Date()
        val formatter = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
        val formattedDate = formatter.format(currentDate)
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
        view.findViewById<TextView>(R.id.dateTv).text = formattedDate
        view.findViewById<TextView>(R.id.dayTv).text = dayOfWeek

        val user = FirebaseAuth.getInstance().currentUser
        val userName = user?.displayName.toString()
        // getting only the first name of the user
        val userNameParts = userName?.split(" ")
        val firstName = userNameParts?.get(0)
        view.findViewById<TextView>(R.id.welcomeTextDashboard).text = "Welcome $firstName"

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

        view.findViewById<CardView>(R.id.journalsCard).setOnClickListener {
            navigateToDestinationFragment(JournalsFragment())
        }
        view.findViewById<CardView>(R.id.connectCard).setOnClickListener {
            navigateToDestinationFragment(ConnectFragment())
        }
        view.findViewById<CardView>(R.id.selCareCard).setOnClickListener {
            navigateToDestinationFragment(SelfCareFragment())
        }

        return view
    }

    private fun navigateToDestinationFragment(fragment: Fragment) {

        // Get the FragmentManager
        val fragmentManager = requireActivity().supportFragmentManager

        // Start a FragmentTransaction
        val transaction = fragmentManager.beginTransaction()

        // Replace the current fragment with the destination fragment
        transaction.replace(R.id.framelayout, fragment)

        // Add the transaction to the back stack (optional)
        transaction.addToBackStack(null)

        // Commit the transaction
        transaction.commit()
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