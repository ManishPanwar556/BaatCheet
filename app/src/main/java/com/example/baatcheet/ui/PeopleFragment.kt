package com.example.baatcheet.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baatcheet.R
import com.example.baatcheet.adapter.MyAdapter
import com.example.baatcheet.clickInterface.ClickInterface
import com.example.baatcheet.room.UserEntity
import com.example.baatcheet.viewModel.UsersViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PeopleFragment : Fragment(),ClickInterface {
    companion object{
        val db=FirebaseFirestore.getInstance()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_people, container, false)
        val rev=view.findViewById<RecyclerView>(R.id.peopleRev)
        val viewModel= UsersViewModel(activity?.application!!)
        db.collection("users").addSnapshotListener { value, error ->
            value?.documents?.forEach {
                if(it.id!=FirebaseAuth.getInstance().currentUser?.uid) {
                    Log.e("DocumentSnapshot", "${it.getString("name")} ${it.id}")
                    val userEntity = UserEntity("${it.getString("name")}", "${it.id}")
                    viewModel.insertUserData(userEntity)
                }
            }
        }
        viewModel.properties.observe(this, Observer {
            rev.adapter=MyAdapter(it,this)
            rev.layoutManager=LinearLayoutManager(activity?.applicationContext,LinearLayoutManager.VERTICAL,false)
        })
        return view
    }

    override fun onClick(uid: String) {
        val intent= Intent(activity,MessageActivity::class.java)
        intent.putExtra("id",uid)
        startActivity(intent)
    }

}