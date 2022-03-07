package com.example.breakingbadproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.breakingbadproject.model.AnyTypeConverter
import com.example.breakingbadproject.model.CharactersModelItem
import com.example.breakingbadproject.model.Converters
import com.example.breakingbadproject.model.IntTypeConverter

@Database(entities = arrayOf(CharactersModelItem::class), version = 1)
@TypeConverters(Converters::class, IntTypeConverter::class, AnyTypeConverter::class)
abstract class CharactersDatabase : RoomDatabase() {

    abstract fun characterDao() : CharactersDAO

    // Singleton

    companion object {

        @Volatile private var instance : CharactersDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CharactersDatabase::class.java,
            "charactersdatabase"
        ).build()
    }
}