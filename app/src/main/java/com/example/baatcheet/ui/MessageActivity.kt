package com.example.baatcheet.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.baatcheet.R

class MessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        actionBar?.setDisplayShowCustomEnabled(true)
        actionBar?.setCustomView(R.layout.actionbar_message)
        
    }
}