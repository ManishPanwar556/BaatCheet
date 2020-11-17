package com.example.baatcheet.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "message_entity")
data class MessageEntity(
    var messages:List<String>,
    var personalUid:String,
    @PrimaryKey
    var otherUid:String
)
@Entity(tableName = "user_entity")
data class UserEntity(
    var name: String?,
    @PrimaryKey
    var otherUid: String
)