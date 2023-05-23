package com.project.mindmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.project.mindmate.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var sendEmail : Button
    private lateinit var forgotPassEmail : EditText
    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //created method for reset password!
        resetPassword()

    }

    private fun resetPassword(){
        auth = FirebaseAuth.getInstance()
        sendEmail = findViewById(R.id.sendEmailBtn)
        forgotPassEmail = findViewById(R.id.forgotPasswordEmail)
        sendEmail.setOnClickListener {
            val email = forgotPassEmail.text.toString()

            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    Toast.makeText(this, "check your email", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }
}