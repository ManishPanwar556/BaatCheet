package com.example.baatcheet.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.baatcheet.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthSettings
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {
    companion object{
        const val REQUEST_CODE=123
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val cameraIcon=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.iv_camera)
        val userName=findViewById<TextView>(R.id.userName)
        val mailIdText=findViewById<TextView>(R.id.mailId)
        val currentUser=FirebaseAuth.getInstance().currentUser
        val mailId=currentUser?.email
        val firestore=FirebaseFirestore.getInstance()
        firestore.collection("users").document("${currentUser?.uid}").addSnapshotListener { value, error ->
            mailIdText.text=mailId
            userName.text=value?.get("name").toString()
        }
        cameraIcon.setOnClickListener {
            val intent=Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== REQUEST_CODE){
            if(resultCode== RESULT_OK){
                val data=data?.data
                val profile=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.profilePic)
                Glide.with(this).load(data).into(profile)
                val profileBtn=findViewById<MaterialButton>(R.id.updateProfileBtn)
                profileBtn.visibility= View.VISIBLE
            }
        }
    }
}