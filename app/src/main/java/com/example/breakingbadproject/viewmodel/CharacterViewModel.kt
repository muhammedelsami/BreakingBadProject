package com.example.breakingbadproject.viewmodel

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.breakingbadproject.data.ApiClient
import com.example.breakingbadproject.data.ApiService
import com.example.breakingbadproject.model.CharactersModelItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterViewModel : ViewModel() {
    lateinit var apiService: ApiService
    lateinit var charList: MutableList<CharactersModelItem>

    val charMutableLiveData : MutableLiveData<List<CharactersModelItem>> = MutableLiveData()
    val errorMessage = MutableLiveData<Boolean>()
    val progressBar = MutableLiveData<Boolean>()

    fun getCharacters() {
        progressBar.value = true

        apiService = ApiClient.getClient().create(ApiService::class.java)

        val character = apiService.characterList()

        character.enqueue(object : Callback<List<CharactersModelItem>> {
            override fun onResponse(
                call: Call<List<CharactersModelItem>>,
                response: Response<List<CharactersModelItem>>
            ) {
                if (response.isSuccessful) {
                    charMutableLiveData.value = response.body()
                    errorMessage.value = false
                    progressBar.value = false
                }
            }

            override fun onFailure(call: Call<List<CharactersModelItem>>, t: Throwable) {
                errorMessage.value = true
                progressBar.value = false
                t.printStackTrace()
            }

        })
    }
}