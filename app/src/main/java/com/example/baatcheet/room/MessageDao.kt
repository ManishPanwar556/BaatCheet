package com.example.baatcheet.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(messageEntity: MessageEntity)

    @Query("SELECT * FROM message_table where id=:pid Order by timeStamp ASC")
    suspend fun getAllMessages(pid: String): List<MessageEntity>

    @Query("DELETE FROM message_table where id=:pid")
    suspend fun deleteAllMessages(pid: String)
}