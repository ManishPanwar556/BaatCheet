package com.example.baatcheet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.baatcheet.R
import com.example.baatcheet.room.MessageEntity
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter:androidx.recyclerview.widget.ListAdapter<MessageEntity,MessageAdapter.MessageViewHolder>(MessageDiffCallBack()){
    inner class MessageViewHolder(val view: View):RecyclerView.ViewHolder(view)
    class MessageDiffCallBack:DiffUtil.ItemCallback<MessageEntity>(){
        override fun areItemsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
            return oldItem.timeStamp==newItem.timeStamp
        }

        override fun areContentsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
            return oldItem==newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.chat_item,parent,false)
       return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
       val messageEntity=getItem(position)
        val ourTextView=holder.view.findViewById<TextView>(R.id.ourUserMessage)
        val otherTextView=holder.view.findViewById<TextView>(R.id.otherUserMessage)
        val ourCardView=holder.view.findViewById<CardView>(R.id.ourCardView)
        val otherCardView=holder.view.findViewById<CardView>(R.id.otherCardView)
        if(messageEntity.senderId==FirebaseAuth.getInstance().currentUser?.uid){
            otherCardView.visibility=View.GONE
            ourTextView.text=messageEntity.message
        }
        else{
            ourCardView.visibility=View.GONE
            otherTextView.text=messageEntity.message
        }
    }
}



