package com.project.mindmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.mindmate.Adapter.MoodLogsAdapter
import com.project.mindmate.Adapter.SleepLogsAdapter
import com.project.mindmate.Adapter.WaterIntakeAdapter
import com.project.mindmate.Models.MoodLogsModel
import com.project.mindmate.Models.SleepLogsModel
import com.project.mindmate.Models.WaterIntakeModel
import com.project.mindmate.databinding.ActivityTrackingStatsBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TrackingStatsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrackingStatsBinding

    // vars for mood recycler view
    private lateinit var moodsList: ArrayList<MoodLogsModel>
    private lateinit var moodAdapter: MoodLogsAdapter
    private lateinit var moodImg: Array<Int>
    private lateinit var moodText: Array<String>

    // for mood triggers
    private lateinit var moodTriggersChipGroup : ChipGroup

    // var for sleep recycler view
    private lateinit var sleepList: ArrayList<SleepLogsModel>
    private lateinit var sleepAdapter: SleepLogsAdapter
    private lateinit var sleepImg: Array<Int>
    private lateinit var sleepText: Array<String>

    // vars for water recycler view
    private lateinit var waterList : ArrayList<WaterIntakeModel>
    private lateinit var waterAdapter : WaterIntakeAdapter
    private lateinit var waterImg : Array<Int>

    //firestore
    private lateinit var firestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackingStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        moodsList = arrayListOf<MoodLogsModel>()
        moodAdapter = MoodLogsAdapter(moodsList)
        moodDataInitialize()

        // chips for mood triggers
        moodTriggersChipGroup = binding.chipGroupMoodTriggers
        val moodTriggers = listOf("Family", "Friends", "Work", "Relationship/Marriage", "Deadline", "Studies", "Exams")
        for (trigger in moodTriggers) {
            val chip = layoutInflater.inflate(
                R.layout.chip_mood_trigger,
                moodTriggersChipGroup,
                false
            ) as Chip
            chip.text = trigger
            moodTriggersChipGroup.addView(chip)
        }

        sleepList = arrayListOf<SleepLogsModel>()
        sleepAdapter = SleepLogsAdapter(sleepList)
        sleepDataInitialize()

        waterList = arrayListOf<WaterIntakeModel>()
        waterAdapter = WaterIntakeAdapter(waterList)
        waterDataInitialize()

        binding.saveLogsBtn.setOnClickListener {
            saveLogs()
            startActivity(Intent(this, HomeActivity::class.java))
        }
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
                val item = waterList[position]
                if (item.isOriginalImage) {
                    // If the current image is the original image, change it to a new image
                    item.image = R.drawable.full_glass // Replace "new_image_resource" with the desired new image resource
                    item.isOriginalImage = false
                } else {
                    // If the current image is already changed, revert it back to the original image
                    item.image = R.drawable.empty_glass
                    item.isOriginalImage = true
                }

                // Update the RecyclerView item at the clicked position
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

    fun getNumberOfFullGlasses(): Int {
        var numberOfFullGlasses = 0

        for (item in waterList) {
            if (item.image == R.drawable.full_glass) {
                numberOfFullGlasses++
            }
        }

        return numberOfFullGlasses
    }

    private fun getSelectedTriggers(): ArrayList<String> {
        val selectedTriggers = arrayListOf<String>()
        for (i in 0 until moodTriggersChipGroup.childCount) {
            val chip = moodTriggersChipGroup.getChildAt(i) as Chip
            if (chip.isChecked) {
                selectedTriggers.add(chip.text.toString())
            }
        }
        return selectedTriggers
    }

    private fun saveLogs() {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        val selectedMoodPosition = moodAdapter.getSelectedMoodPosition()
        val selectedMoodData = moodsList[selectedMoodPosition]
        val selectedTriggers = getSelectedTriggers()

        val selectedSleepPosition = sleepAdapter.getSelectedSleepPosition()
        val selectedSleepData = sleepList[selectedSleepPosition]

        val noOfFullGlasses = getNumberOfFullGlasses()
        val notes = binding.additionalNotesEt.text.toString()
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val timestamp = Timestamp.now()

        val logsDocument = firestore.collection("users").document(uid).collection("daily_logs").document(
            currentDate
        )
        // Save the log data to the document
        val logData = hashMapOf(
            "mood" to selectedMoodData.textMood,
            "triggers" to selectedTriggers,
            "sleep_quality" to selectedSleepData.textSleep,
            "water_intake" to noOfFullGlasses,
            "notes" to notes,
            "timestamp" to timestamp
        )
        logsDocument.set(logData).addOnSuccessListener {
            Toast.makeText(this, "Logs saved successfully!", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {error ->
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()

        }



    }
    private fun setSelectedCard(position: Int) {
        moodAdapter.setSelectedItem(position)
        sleepAdapter.setSelectedItem(position)
    }
}