package com.example.baatcheet.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.baatcheet.room.DataBase
import com.example.baatcheet.room.MessageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MessageViewModel(application: Application,var id:String) : AndroidViewModel(application) {
    val db by lazy {
        Room.databaseBuilder(application.applicationContext, DataBase::class.java, "messagedb")
            .build()
    }

    private var _properties= MutableLiveData<List<MessageEntity>>()
    val properties:LiveData<List<MessageEntity>>
    get() = _properties
    init {
        updateProperties(id)
    }
    fun updateProperties(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            _properties.postValue(db.messageDao().getAllMessages(id))
        }
    }
    fun insertMessage(messageEntity: MessageEntity){
        viewModelScope.launch (Dispatchers.IO){
           async {
                db.messageDao().insertMessage(messageEntity)
            }.await()
           updateProperties(id)
        }

    }
    fun deleteMessages(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            db.messageDao().deleteAllMessages(id)
        }
    }



}