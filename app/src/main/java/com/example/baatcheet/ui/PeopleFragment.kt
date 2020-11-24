package com.example.baatcheet.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baatcheet.R
import com.example.baatcheet.adapter.MyAdapter
import com.example.baatcheet.clickInterface.ClickInterface
import com.example.baatcheet.models.UserEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PeopleFragment : Fragment(), ClickInterface {
    companion object {
        val db = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_people, container, false)
        val rev = view.findViewById<RecyclerView>(R.id.peopleRev)
        val list = arrayListOf<UserEntity>()
        var map=HashSet<String>()
        db.collection("users").addSnapshotListener { value, error ->
            value?.documents?.forEach{
                if (it.id != FirebaseAuth.getInstance().currentUser?.uid&&!map.contains(it.id)) {
                    val user = UserEntity(it.getString("name"), it.id)
                    list.add(user)
                    map.add(it.id)
                    rev.adapter = MyAdapter(list,this)
                    rev.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                }
            }
        }

        return view
    }

    override fun onClick(uid: String) {
        val intent = Intent(activity, MessageActivity::class.java)
        intent.putExtra("id", uid)
        startActivity(intent)
    }

}