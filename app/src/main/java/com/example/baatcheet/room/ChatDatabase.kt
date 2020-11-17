package com.example.baatcheet.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MessageEntity::class, UserEntity::class],version = 1)
@TypeConverters(DataConverter::class)
abstract class ChatDatabase :RoomDatabase() {

    abstract fun chatDao():Dao
}