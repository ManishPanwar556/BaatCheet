package com.example.baatcheet.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.baatcheet.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {
    companion object {
        val mAuth = FirebaseAuth.getInstance()
        val fireStore = FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val signUpBtn = findViewById<MaterialButton>(R.id.signUpBtn)
        val email = findViewById<EditText>(R.id.emailEditText)
        val password = findViewById<EditText>(R.id.passwordEditText)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val signIn = findViewById<TextView>(R.id.signin)

        signUpBtn.setOnClickListener {
            if (!email.text.isEmpty() && !password.text.isEmpty() && !nameEditText.text.isEmpty()) {
                mAuth.createUserWithEmailAndPassword(
                    email.text.toString(),
                    password.text.toString()
                ).addOnCompleteListener{
                    val map = HashMap<String, Any>()
                    val uid = mAuth.uid
                    val name = nameEditText.text.toString()
                    map.put("name",name)
                    fireStore.collection("users").document("$uid").set(map).addOnSuccessListener{
                            val intent =
                                Intent(this@SignUpActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(
                                this@SignUpActivity,
                                "Data uploading Failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Fill The Empty Field", Toast.LENGTH_SHORT).show()
            }

            signIn.setOnClickListener {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


        override fun onStart() {
            super.onStart()
            if (mAuth.currentUser != null) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }


        }
    }