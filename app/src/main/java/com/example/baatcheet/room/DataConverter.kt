package com.example.baatcheet.room

import androidx.room.TypeConverter


class DataConverter {
    @TypeConverter
    fun toString(list:List<String>):String{
        return list.joinToString(separator = ",")
    }
    @TypeConverter
    fun fromString(str:String):List<String>{
       return str.split(",").map {
            it
        }
    }

}