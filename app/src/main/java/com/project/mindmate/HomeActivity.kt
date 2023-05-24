package com.project.mindmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var frameLayout: FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        replaceFragment(HomeFragment())
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        frameLayout = findViewById(R.id.framelayout)
        bottomNavigationView.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.journal -> {
                    replaceFragment(JournalsFragment())
                    true
                }
                R.id.selfCare -> {
                    replaceFragment(SelfCareFragment())
                    true
                }
                R.id.connect -> {
                    replaceFragment(ConnectFragment())
                    true
                }
                else -> {
                    replaceFragment(ProfileFragment())
                    true
                }
            }
        }
        // going back to the explore fragment after deleting the post from detailed post activity
        val fragmentToDisplay = intent.getStringExtra("explore_fragment")
        if (fragmentToDisplay=="explore_fragment_tag"){
            replaceFragment(ConnectFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.framelayout, fragment)
        fragmentTransaction.commit()
    }
}