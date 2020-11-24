package com.example.baatcheet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.baatcheet.R
import com.example.baatcheet.clickInterface.ClickInterface
import com.example.baatcheet.models.UserEntity

class MyAdapter(var list:ArrayList<UserEntity>,val clickInterface: ClickInterface):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    inner class MyViewHolder(val view: View):RecyclerView.ViewHolder(view){
        init {
            view.findViewById<LinearLayout>(R.id.linearLayout).setOnClickListener {
                if(adapterPosition!=RecyclerView.NO_POSITION){
                    clickInterface.onClick(list.get(adapterPosition).otherUid)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.user_item_row,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val view=holder.view
        val userName=view.findViewById<TextView>(R.id.userFullName)
        userName.text=list.get(position).name
    }

    override fun getItemCount()=list.size

}