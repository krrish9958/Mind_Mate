package com.project.mindmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.mindmate.Adapter.MoodLogsAdapter
import com.project.mindmate.Adapter.SleepLogsAdapter
import com.project.mindmate.Adapter.WaterIntakeAdapter
import com.project.mindmate.Models.MoodLogsModel
import com.project.mindmate.Models.SleepLogsModel
import com.project.mindmate.Models.WaterIntakeModel
import com.project.mindmate.databinding.ActivityTrackingStatsBinding

class TrackingStatsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrackingStatsBinding

    // vars for mood recycler view
    private lateinit var moodsList: ArrayList<MoodLogsModel>
    private lateinit var moodAdapter: MoodLogsAdapter
    private lateinit var moodImg: Array<Int>
    private lateinit var moodText: Array<String>

    // var for sleep recycler view
    private lateinit var sleepList: ArrayList<SleepLogsModel>
    private lateinit var sleepAdapter: SleepLogsAdapter
    private lateinit var sleepImg: Array<Int>
    private lateinit var sleepText: Array<String>

    // vars for water recycler view
    private lateinit var waterList : ArrayList<WaterIntakeModel>
    private lateinit var waterAdapter : WaterIntakeAdapter
    private lateinit var waterImg : Array<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackingStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        moodsList = arrayListOf<MoodLogsModel>()
        moodAdapter = MoodLogsAdapter(moodsList)
        moodDataInitialize()

        sleepList = arrayListOf<SleepLogsModel>()
        sleepAdapter = SleepLogsAdapter(sleepList)
        sleepDataInitialize()

        waterList = arrayListOf<WaterIntakeModel>()
        waterAdapter = WaterIntakeAdapter(waterList)
        waterDataInitialize()

        binding.backImg.setOnClickListener {
            onBackPressed()
        }
    }

    private fun waterDataInitialize() {
        waterImg = arrayOf(
            R.drawable.empty_glass,
            R.drawable.empty_glass,
            R.drawable.empty_glass,
            R.drawable.empty_glass,
            R.drawable.empty_glass,
            R.drawable.empty_glass,
            R.drawable.empty_glass,
            R.drawable.empty_glass
        )

        for (i in waterImg.indices){
            val data = WaterIntakeModel(waterImg[i])
            waterList.add(data)
        }
        binding.waterRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.waterRecyclerView.setHasFixedSize(true)
        binding.waterRecyclerView.adapter = waterAdapter
        waterAdapter.setOnItemClickListener(object : WaterIntakeAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                waterImg[position] = R.drawable.full_glass
                waterList[position] = WaterIntakeModel(waterImg[position])
                // Notify the adapter that the item at the clicked position has changed
                waterAdapter.notifyItemChanged(position)
            }

        })
    }

    private fun sleepDataInitialize() {
        sleepImg = arrayOf(
            R.drawable.numb,
            R.drawable.tired,
            R.drawable.dead,
            R.drawable.smile,
            R.drawable.silent
        )

        sleepText = arrayOf(
            "Great",
            "Bad",
            "Terrible",
            "Good",
            "Okay"

        )

        for (i in sleepImg.indices) {
            val data = SleepLogsModel(sleepImg[i], sleepText[i])
            sleepList.add(data)
        }
        binding.sleepRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.sleepRecyclerView.setHasFixedSize(true)
        binding.sleepRecyclerView.adapter = sleepAdapter
    }

    private fun moodDataInitialize() {
        moodImg = arrayOf(
            R.drawable.smile,
            R.drawable.numb,
            R.drawable.sad,
            R.drawable.silent,
            R.drawable.angry,
            R.drawable.dead,
            R.drawable.tired,
            R.drawable.shocked,
            R.drawable.love
        )

        moodText = arrayOf(
            "Happy",
            "Sad",
            "Stressed",
            "Okay",
            "Angry",
            "Tired",
            "Anxious",
            "Shocked",
            "Great"
        )

        for (i in moodImg.indices) {
            val data = MoodLogsModel(moodImg[i], moodText[i])
            moodsList.add(data)
        }
        binding.moodRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.moodRecyclerView.setHasFixedSize(true)
        binding.moodRecyclerView.adapter = moodAdapter
    }

    private fun saveLogs() {

    }
}