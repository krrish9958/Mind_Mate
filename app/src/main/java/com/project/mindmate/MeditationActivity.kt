package com.project.mindmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.project.mindmate.databinding.ActivityMeditationBinding

class MeditationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMeditationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeditationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.startMeditationButton.setOnClickListener {
            val bottomSheetFragment = MeditationBottomSheetFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }

    }
}