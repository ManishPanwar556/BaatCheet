package com.example.baatcheet.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.example.baatcheet.adapter.FragmentsAdapter
import com.example.baatcheet.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        val auth = FirebaseAuth.getInstance()
        val cloudFireStore=FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.elevation=0f
        val viewPager2 = findViewById<ViewPager2>(R.id.myViewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabsLayout)
        tabLayout.setTabTextColors(Color.parseColor("#808080"),Color.parseColor("#ffffff"))
        viewPager2.adapter = FragmentsAdapter(this)
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            if (position == 0) {
                tab.text = "Chats"
            } else {
                tab.text = "Peoples"
            }

        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logOut -> {
                auth.signOut()
                startActivity(Intent(this, SignUpActivity::class.java))
                finish()
            }
            R.id.profile->{
                startActivity(Intent(this,ProfileActivity::class.java))
            }
        }
        return true
    }


}