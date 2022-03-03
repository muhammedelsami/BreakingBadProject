package com.example.breakingbadproject.adapter

import android.view.View
import com.example.breakingbadproject.model.CharactersModelItem

interface CharacterClickListener {
    fun characterClicked(view: View, ch : CharactersModelItem)
}