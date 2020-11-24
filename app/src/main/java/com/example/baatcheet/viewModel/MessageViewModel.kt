package com.example.baatcheet.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.baatcheet.room.DataBase
import com.example.baatcheet.room.MessageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageViewModel(application: Application,id:String) : AndroidViewModel(application) {
    val db by lazy {
        Room.databaseBuilder(application.applicationContext, DataBase::class.java, "messagedb")
            .build()
    }
    var properties = MutableLiveData<List<MessageEntity>>()

    init {
        updateProperties(id)
    }
    fun updateProperties(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            properties.postValue(db.messageDao().getAllMessages(id))
        }
    }
    fun insertMessage(messageEntity: MessageEntity){
        viewModelScope.launch(Dispatchers.IO) {
            db.messageDao().insertMessage(messageEntity)
        }
    }
    fun deleteMessages(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            db.messageDao().deleteAllMessages(id)
        }
    }



}