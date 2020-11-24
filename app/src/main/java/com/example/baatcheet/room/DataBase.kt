package com.example.baatcheet.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MessageEntity::class],version = 1)
abstract class DataBase:RoomDatabase() {
    abstract fun messageDao():MessageDao
}