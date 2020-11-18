package com.example.baatcheet.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.baatcheet.R
import com.google.firebase.firestore.FirebaseFirestore

class MessageActivity : AppCompatActivity() {
    companion object{
        val db=FirebaseFirestore.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        val actionBar=supportActionBar
        actionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayShowHomeEnabled(false)
            setDisplayShowCustomEnabled(true)
            setCustomView(R.layout.actionbar_message)
        }
        val name=findViewById<TextView>(R.id.name)
        val id=intent.extras?.get("id")
        db.collection("users").document("$id").addSnapshotListener { value, error ->
            name.text=value?.get("name").toString()

        }

    }
}