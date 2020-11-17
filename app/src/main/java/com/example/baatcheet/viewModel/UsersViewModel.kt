package com.example.baatcheet.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.baatcheet.clickInterface.ClickInterface
import com.example.baatcheet.room.ChatDatabase
import com.example.baatcheet.room.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UsersViewModel(application: Application):AndroidViewModel(application) {
   private val db by lazy {
        Room.databaseBuilder(application, ChatDatabase::class.java,"chat-db").build()
    }
    var properties=MutableLiveData<List<UserEntity>>()
    init {
        updateProperties()
    }
    fun updateProperties(){
       GlobalScope.launch(Dispatchers.IO) {
           properties.postValue(db.chatDao().getUser())
       }
    }
    fun insertUserData(userEntity: UserEntity){
        GlobalScope.launch(Dispatchers.IO) {
            db.chatDao().insertUser(userEntity)
        }
    }
    fun deleteAllUsers(){
        GlobalScope.launch(Dispatchers.IO) {
            db.chatDao().deleteAllUsers()
        }

    }

}