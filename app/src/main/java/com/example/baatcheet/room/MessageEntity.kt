package com.example.baatcheet.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_table")
data class MessageEntity(

    val id: String,
    val senderId: String,
    val receiverId: String,
    val message: String,
    @PrimaryKey(autoGenerate = false)
    val timeStamp: Long
)