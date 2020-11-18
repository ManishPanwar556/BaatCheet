package com.example.baatcheet.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.baatcheet.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class MessageActivity : AppCompatActivity() {
    companion object {
        val db = FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        val actionBar = supportActionBar
        val message = findViewById<EditText>(R.id.messageEditText)
        val sendBtn = findViewById<FloatingActionButton>(R.id.sendBtn)
        actionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayShowHomeEnabled(false)
            setDisplayShowCustomEnabled(true)
            setCustomView(R.layout.actionbar_message)
        }

        val name = findViewById<TextView>(R.id.name)
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val id = intent.extras?.get("id")
        db.collection("users").document("$id").addSnapshotListener { value, error ->
            name.text = value?.get("name").toString()
        }
        sendBtn.setOnClickListener {
            if (!message.text.isEmpty()) {
                val mainMessage = message.text.toString()
                message.getText().clear()
                sendMessageToDataBase(mainMessage, uid, id.toString())

            }
        }

    }

    private fun sendMessageToDataBase(message: String, uid: String?, id: String) {
        var list= arrayListOf<String>()
        db.collection("users").document("$uid").addSnapshotListener{value,error->
           Toast.makeText(this,"${value?.get("$id")}",Toast.LENGTH_LONG).show()

        }
        db.collection("users").document("$uid").update("$id",FieldValue.arrayUnion(list)).addOnSuccessListener {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show()
        }
    }
}