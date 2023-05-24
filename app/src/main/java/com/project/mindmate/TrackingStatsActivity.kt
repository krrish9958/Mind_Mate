package com.project.mindmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.mindmate.databinding.ActivityTrackingStatsBinding

class TrackingStatsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrackingStatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackingStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.moodHappy.setOnClickListener {
            binding.moodHappy.isChecked = !binding.moodHappy.isChecked
        }

        binding.moodSad.setOnClickListener {
            binding.moodSad.isChecked = !binding.moodSad.isChecked
        }

        binding.moodLow.setOnClickListener {
            binding.moodLow.isChecked = !binding.moodLow.isChecked
        }

        binding.moodDepressed.setOnClickListener {
            binding.moodDepressed.isChecked = !binding.moodDepressed.isChecked
        }
        binding.moodOkay.setOnClickListener {
            binding.moodOkay.isChecked = !binding.moodOkay.isChecked
        }

        binding.moodGood.setOnClickListener {
            binding.moodGood.isChecked = !binding.moodGood.isChecked
        }

        binding.sleepGood.setOnClickListener {
            binding.sleepGood.isChecked = !binding.sleepGood.isChecked
        }
        binding.sleepBad.setOnClickListener {
            binding.sleepBad.isChecked = !binding.sleepBad.isChecked
        }
        binding.sleepGreat.setOnClickListener {
            binding.sleepGreat.isChecked = !binding.sleepGreat.isChecked
        }
        binding.sleepTerrible.setOnClickListener {
            binding.sleepTerrible.isChecked = !binding.sleepTerrible.isChecked
        }
        binding.sleepOkay.setOnClickListener {
            binding.sleepOkay.isChecked = !binding.sleepOkay.isChecked
        }

        binding.saveLogsBtn.setOnClickListener {
            saveLogs()
        }
    }

    private fun saveLogs() {

    }
}