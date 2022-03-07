package com.example.breakingbadproject.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.breakingbadproject.model.CharactersModelItem

@Dao
interface CharactersDAO {
    // Data Access Object

    @Insert
    suspend fun insertAll(vararg characters : CharactersModelItem) : List<Long>

    @Query("SELECT * FROM characters")
    suspend fun getAllCharacter() : List<CharactersModelItem>

    @Query("SELECT * FROM characters WHERE uuid = :characterId")
    suspend fun getCharacter(characterId : Int) : CharactersModelItem

    @Query("DELETE FROM characters")
    suspend fun deleteAllCharacter()

    @Delete
    suspend fun deleteCharacter(charactersModelItem: CharactersModelItem)
}