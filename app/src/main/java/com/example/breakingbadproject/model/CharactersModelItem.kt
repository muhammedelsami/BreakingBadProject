package com.example.breakingbadproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "characters")
data class CharactersModelItem(
    //val appearance: List<Int>?, //  open it if you want to save to local database
    //val better_call_saul_appearance: List<Any>?,  //  open it if you want to save to local database
    val birthday: String,
    val category: String,
    val char_id: Int,
    val img: String,
    val name: String,
    val nickname: String,
    //val occupation: List<String>, //  open it if you want to save to local database
    val portrayed: String,
    val status: String
) {
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}