package com.example.breakingbadproject.model

import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object Converters {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        val listType: Type = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

}
object IntTypeConverter {

    @TypeConverter
    fun listToJson(value: List<Int>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Int>::class.java).toList()
}
object AnyTypeConverter {
    @TypeConverter
    fun listToJson(value: List<Any>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Any>::class.java).toList()
}



// Room databaseine list kaydetmek için kullanılır.

//class Converters {
//
//    @TypeConverter
//    fun listToJson(value: List<JobWorkHistory>?) = Gson().toJson(value)
//
//    @TypeConverter
//    fun jsonToList(value: String) = Gson().fromJson(value, Array<JobWorkHistory>::class.java).toList()
//}
