package com.project.mindmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.project.mindmate.databinding.ActivityMeditationTimerBinding
import java.util.Locale

class MeditationTimerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMeditationTimerBinding
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeditationTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Get the duration in minutes from the intent
        val durationMinutes = intent.getIntExtra("DURATION_MIN", 10)

        // Convert the duration to milliseconds
        val totalDurationMillis = durationMinutes * 60 * 1000L

        // Initialize the TextView with the initial time
        val initialMinutes = durationMinutes
        val initialSeconds = 0
        val initialTimeLeft = String.format(Locale.getDefault(), "%02d:%02d", initialMinutes, initialSeconds)
            binding.timerCountdown.text = initialTimeLeft

        // Set the initial progress of the ProgressBar
        binding.progressBarTimer.progress = 100 // Use any value that represents the completion of the timer

        // Initialize the CountDownTimer with the total duration
        timer = object : CountDownTimer(totalDurationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Calculate the progress percentage
                val progress = (millisUntilFinished * 100 / totalDurationMillis).toInt()
                // Update the progress of the ProgressBar
                binding.progressBarTimer.progress = progress

                // Update the TextView with the remaining time in minutes and seconds
                val minutes = millisUntilFinished / (1000 * 60)
                val seconds = (millisUntilFinished / 1000) % 60
                val timeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
                binding.timerCountdown.text = timeLeft

            }
            override fun onFinish() {
                Toast.makeText(applicationContext, "Session ended", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, HomeActivity::class.java))
            }

        }
        timer.start()
        binding.tvEndSession.setOnClickListener {
            timer.cancel()
            Toast.makeText(this, "Session ended", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HomeActivity::class.java))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}