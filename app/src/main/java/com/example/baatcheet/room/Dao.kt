package com.example.baatcheet.room

import android.os.Message
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(messageEntity: MessageEntity)
    @Query("SELECT * from user_entity")
    suspend fun getUser():List<UserEntity>
    @Query("DELETE from user_entity")
    suspend fun deleteAllUsers()
}