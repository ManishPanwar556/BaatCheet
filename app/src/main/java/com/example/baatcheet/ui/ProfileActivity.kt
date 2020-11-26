package com.example.baatcheet.ui

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.baatcheet.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthActionCodeException
import com.google.firebase.auth.FirebaseAuthSettings
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class ProfileActivity : AppCompatActivity() {
    companion object{
        const val REQUEST_CODE=123
        val storage=FirebaseStorage.getInstance().getReference()
        val imageReference=FirebaseStorage.getInstance()
    }
    lateinit var photoUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val uploadBtn=findViewById<MaterialButton>(R.id.updateProfileBtn)
        val cameraIcon=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.iv_camera)
        val userName=findViewById<TextView>(R.id.userName)
        val mailIdText=findViewById<TextView>(R.id.mailId)
        val currentUser=FirebaseAuth.getInstance().currentUser
        val userProfile=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.profilePic)
        val mailId=currentUser?.email
        val fireStore=FirebaseFirestore.getInstance()
        val localFile= File.createTempFile("profile","jpg")
        val uid=FirebaseAuth.getInstance().currentUser?.uid
        val gsReference=imageReference.reference
        Glide.with(this).load(gsReference).into(userProfile)
        fireStore.collection("users").document("${currentUser?.uid}").addSnapshotListener { value, error ->
            mailIdText.text=mailId
            userName.text=value?.get("name").toString()
        }
        cameraIcon.setOnClickListener {
            val intent=Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_CODE)
        }
        uploadBtn.setOnClickListener {
            val folderRef=storage.child("images/${FirebaseAuth.getInstance().currentUser?.uid}/profile.jpg")
            val task= folderRef.putFile(photoUri)
            val alertDialog=AlertDialog.Builder(this)
            val inflater=LayoutInflater.from(this).inflate(R.layout.progress_layout,null)
//            val progressBar=inflater.findViewById<ProgressBar>(R.id.uploadProgressBar)
//            alertDialog.setView(inflater)
//            alertDialog.create()
           task.addOnProgressListener {
//               progressBar.progress=((100.0*it.bytesTransferred)/it.totalByteCount).toInt()
           }
               .addOnPausedListener {

               }.addOnSuccessListener {
               }
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== REQUEST_CODE){
            if(resultCode== RESULT_OK){
                val data=data?.data
                if(data!=null) {
                    photoUri = data
                    val profile =
                        findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.profilePic)
                    Glide.with(this).load(data).into(profile)
                    val profileBtn = findViewById<MaterialButton>(R.id.updateProfileBtn)
                    profileBtn.visibility = View.VISIBLE
                }
            }
        }
    }
}