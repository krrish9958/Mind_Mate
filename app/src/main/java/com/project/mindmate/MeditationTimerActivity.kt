package com.project.mindmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.mindmate.Models.MeditationModel
import com.project.mindmate.databinding.ActivityMeditationTimerBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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
                finish()
                saveMeditationSession()
                startActivity(Intent(applicationContext, HomeActivity::class.java))
            }
        }
        timer.start()

        binding.tvEndSession.setOnClickListener {
            timer.cancel()
            finish()
            saveMeditationSession()
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    private fun saveMeditationSession() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userUid = currentUser?.uid
        val firestore = FirebaseFirestore.getInstance()

        if (userUid != null) {
            val durationMinutes = intent.getIntExtra("DURATION_MIN", 10)

            // Get IST (Indian Standard Time) zone and format
            val istTimeZone = TimeZone.getTimeZone("Asia/Kolkata")
            val istDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            istDateFormat.timeZone = istTimeZone
            val startTimeString = istDateFormat.format(Date())

            // Calculate end time based on duration
            val endTimeMillis = System.currentTimeMillis() + (durationMinutes * 60 * 1000)
            val endTimeString = istDateFormat.format(endTimeMillis)

            val timestamp = Timestamp.now()
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            val meditationSession = MeditationModel(durationMinutes, startTimeString, endTimeString, timestamp, currentDate)

            firestore.collection("users").document(userUid).collection("meditation_sessions")
                .add(meditationSession)
                .addOnSuccessListener {
                    Toast.makeText(this, "Session ended and saved successfully!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}
