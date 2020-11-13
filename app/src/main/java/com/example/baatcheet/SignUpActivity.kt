package com.example.baatcheet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    companion object {
        val mAuth=FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val signUpBtn=findViewById<MaterialButton>(R.id.signUpBtn)
        val email=findViewById<EditText>(R.id.emailEditText)
        val password=findViewById<EditText>(R.id.passwordEditText)
        val signIn=findViewById<TextView>(R.id.signin)
        signUpBtn.setOnClickListener {
            if(!email.text.isEmpty()&&!password.text.isEmpty()){
                mAuth.createUserWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Signed In Successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Error In Signing Up", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(this,"Fill the empty field",Toast.LENGTH_SHORT).show()
            }
        }
        signIn.setOnClickListener {
            val intent=Intent(this,SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onStart() {
        super.onStart()
        if(mAuth.currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }


    }
}