package com.example.breakingbadproject.adapter

import android.view.View
import com.example.breakingbadproject.model.CharactersModelItem

interface CharacterClickDelete {
    fun characterDelete(view: View, char : CharactersModelItem)
}