package com.example.baatcheet.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.model.StringLoader
import com.example.baatcheet.R
import com.example.baatcheet.adapter.MessageAdapter
import com.example.baatcheet.adapter.MyAdapter
import com.example.baatcheet.room.MessageEntity
import com.example.baatcheet.viewModel.MessageViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.seconds

class MessageActivity : AppCompatActivity() {
    companion object {
        val db = FirebaseFirestore.getInstance()
    }

    lateinit var viewModel: MessageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        val actionBar = supportActionBar
        val message = findViewById<EditText>(R.id.messageEditText)
        val sendBtn = findViewById<FloatingActionButton>(R.id.sendBtn)
        val rev = findViewById<RecyclerView>(R.id.messageRecyclerView)
        val id = intent.extras?.get("id").toString()
        viewModel = MessageViewModel(application, id)
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        actionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayShowHomeEnabled(false)
            setDisplayShowCustomEnabled(true)
            setCustomView(R.layout.actionbar_message)
        }
        val textView = actionBar?.customView?.findViewById<TextView>(R.id.name)
        db.collection("users").document("$id").addSnapshotListener { value, error ->
            textView?.text = value?.get("name").toString()
        }
        updateRoom(id,uid!!,rev)
        sendBtn.setOnClickListener {
            if (!message.text.isEmpty()) {
                val newMesssage = message.text.toString()
                message.text.clear()
                saveMessageToDataBase(newMesssage, id, uid!!, rev)
            }
        }
    }


    private fun saveMessageToDataBase(message: String, id: String, uid: String, rev: RecyclerView) {
        val toDocsRef = db.collection("users").document("$uid")
        val fromDocsRef = db.collection("users").document("$id")
        val toRef = toDocsRef.collection("messages").document("$id")
        val fromRef = fromDocsRef.collection("messages").document("$uid")
        db.runTransaction { transaction ->
            val toSnapshot = transaction.get(toRef)
            val fromSnapshot = transaction.get(fromRef)
            val time = System.currentTimeMillis()
            if (toSnapshot.get("to") == null) {
                Log.e("Transaction", "Null")
                val toData = hashMapOf<String, Any>(
                    "to" to arrayListOf<String>(message),
                    "toTimeStamp" to arrayListOf<Long>(time)
                )
                transaction.update(toRef, toData)
                val fromData = hashMapOf<String, Any>(
                    "from" to arrayListOf<String>(message),
                    "fromTimeStamp" to arrayListOf<Long>(time)
                )
                transaction.update(fromRef, fromData)
            } else {
                Log.e("Transaction", "Not Null")
                val toList = toSnapshot.get("to") as ArrayList<String>
                val fromList = fromSnapshot.get("from") as ArrayList<String>
                toList.add(message)
                fromList.add(message)
                transaction.update(toRef, "to", toList)
                transaction.update(toRef, "toTimeStamp", FieldValue.arrayUnion(time))
                transaction.update(fromRef, "from", fromList)
                transaction.update(fromRef, "fromTimeStamp", FieldValue.arrayUnion(time))
            }
        }.addOnSuccessListener {
            updateRoom(id, uid, rev)
        }.addOnFailureListener {
            Log.e("Transaction", "${it.message}")
        }
    }

    private fun updateRoom(id: String, uid: String, rev: RecyclerView) {
        val docsRef = db.collection("users").document("$uid").collection("messages").document("$id")
        docsRef.addSnapshotListener { value, error ->
            val toList = value?.get("to") as ArrayList<String>?
            val toTimeStamp = value?.get("toTimeStamp") as ArrayList<Long>?
            val fromList = value?.get("from") as ArrayList<String>?
            val fromTimeStamp = value?.get("fromTimeStamp") as ArrayList<Long>?
            if (toList != null && toTimeStamp != null) {
                var count = 0
                toList.forEach {
                    val messageEntity = MessageEntity(id, uid, id, it, toTimeStamp[count])
                    viewModel.insertMessage(messageEntity)
                    count++
                }
            }
            if (fromList != null && fromTimeStamp != null) {
                var count = 0
                fromList.forEach {
                    val messageEntity = MessageEntity(id, id, uid, it, fromTimeStamp[count])
                    viewModel.insertMessage(messageEntity)
                    count++
                }

            }
            updateLiveData(rev)
        }

    }

    private fun updateLiveData(rev: RecyclerView) {
        val adapter = MessageAdapter()
        rev.adapter=adapter

        viewModel.properties.observe(this, Observer {
            Log.e("Livedata", "$it")
            adapter.submitList(it)
            rev.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            rev.scrollToPosition(adapter.itemCount-1)
        })
    }


}