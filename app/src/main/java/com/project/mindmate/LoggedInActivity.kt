package com.project.mindmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.project.mindmate.databinding.ActivityLoggedInBinding

class LoggedInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoggedInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoggedInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val user = FirebaseAuth.getInstance().currentUser
        val userName = user?.displayName.toString()
        // getting only the first name of the user
        val userNameParts = userName?.split(" ")
        val firstName = userNameParts?.get(0)
        binding.welcomeUserTv.text = "Welcome ${firstName}"

       binding.navigateToHomeBtn.setOnClickListener {
           val intent = Intent(this , HomeActivity::class.java)
           startActivity(intent)
       }

        binding.navigateToFeelingsBtn.setOnClickListener {
            val intent = Intent(this , FeelingsActivity::class.java)
            startActivity(intent)
        }
    }
}