package com.example.breakingbadproject.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.breakingbadproject.util.Constants.Companion.SAVED_TIME

class MySharedPreferences {
    companion object {
        private var sharedPreferences : SharedPreferences? = null

        @Volatile private var instance : MySharedPreferences? = null
        private val lock = Any()
        operator fun invoke(context: Context) : MySharedPreferences = instance ?: synchronized(lock) {
            instance ?: createSharedPreference(context).also {
                instance = it
            }
        }

        private fun createSharedPreference(context: Context) : MySharedPreferences {
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return MySharedPreferences()
        }
    }
    fun savedTime( time : Long) {
        sharedPreferences?.edit(commit = true){
            putLong(SAVED_TIME, time)
        }
    }

    fun getSavedTime() = sharedPreferences?.getLong(SAVED_TIME, 0)
}