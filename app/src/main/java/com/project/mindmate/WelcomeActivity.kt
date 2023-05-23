package com.project.mindmate

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.ImageViewTarget
import com.google.firebase.auth.FirebaseAuth
import com.project.mindmate.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWelcomeBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        Glide.with(this)
            .asGif()
            .load(R.drawable.welcome)
            .into(object : ImageViewTarget<GifDrawable>(binding.welcomeGif){
                override fun setResource(resource: GifDrawable?) {
                    resource?.setColorFilter(Color.rgb(136, 121, 212), PorterDuff.Mode.MULTIPLY)
                    binding.welcomeGif.setImageDrawable(resource)
                }
            })

        binding.welcomeLogin.setOnClickListener{
            startActivity(Intent(this@WelcomeActivity,LoginActivity::class.java))
        }
        binding.welcomeSignup.setOnClickListener{
            startActivity(Intent(this@WelcomeActivity,SignUpActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            startActivity(Intent(this@WelcomeActivity, FeelingsActivity::class.java))
            finish()
        }
    }
}