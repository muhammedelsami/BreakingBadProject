package com.example.breakingbadproject.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbadproject.adapter.CharacterClickDelete
import com.example.breakingbadproject.adapter.CharacterClickListener
import com.example.breakingbadproject.adapter.CharactersAdapter
import com.example.breakingbadproject.data.ApiClient
import com.example.breakingbadproject.data.ApiService
import com.example.breakingbadproject.database.CharactersDatabase
import com.example.breakingbadproject.model.CharactersModel
import com.example.breakingbadproject.model.CharactersModelItem
import com.example.breakingbadproject.util.MySharedPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterViewModel(application: Application) : BaseViewModel(application) {
    lateinit var apiService: ApiService
    lateinit var charList: MutableList<CharactersModelItem>

    val charMutableLiveData: MutableLiveData<List<CharactersModelItem>> = MutableLiveData()
    val errorMessage = MutableLiveData<Boolean>()
    val progressBar = MutableLiveData<Boolean>()

    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    private val mySharedPreferences = MySharedPreferences(getApplication())

    fun refresh() {
        val savedTime = mySharedPreferences.getSavedTime()
        if (savedTime != null && savedTime != 0L && System.nanoTime() - savedTime < refreshTime) {
            getCharactersFromDB()
        } else {
            getCharactersFromNetwork()
        }
    }

    fun refreshFromNetwork() {
        getCharactersFromNetwork()
    }

    fun getCharactersFromDB() {
        progressBar.value = true
        launch {
            val charactersList =
                CharactersDatabase.invoke(getApplication()).characterDao().getAllCharacter()
            viewData(charactersList)
            Toast.makeText(getApplication(), "Data From Database", Toast.LENGTH_SHORT).show()
        }
    }

    fun getCharactersFromNetwork() {
        progressBar.value = true

        apiService = ApiClient.getClient().create(ApiService::class.java)

        val character = apiService.characterList()

        character.enqueue(object : Callback<List<CharactersModelItem>> {
            override fun onResponse(
                call: Call<List<CharactersModelItem>>,
                response: Response<List<CharactersModelItem>>
            ) {
                if (response.isSuccessful) {
                    saveToDatabase(response.body()!!)
                    Toast.makeText(getApplication(), "Data From Network", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<List<CharactersModelItem>>, t: Throwable) {
                errorMessage.value = true
                progressBar.value = false
                t.printStackTrace()
            }

        })
    }

    private fun viewData(charactersList: List<CharactersModelItem>) {
        charMutableLiveData.value = charactersList
        errorMessage.value = false
        progressBar.value = false
    }

    private fun saveToDatabase(charactersList: List<CharactersModelItem>) {
        launch {
            val dao = CharactersDatabase.invoke(getApplication()).characterDao()
            dao.deleteAllCharacter()
            val uuidList = dao.insertAll(*charactersList.toTypedArray())
            var i = 0
            while (i < charactersList.size) {
                charactersList[i].uuid = uuidList[i].toInt()
                i++
            }
            viewData(charactersList)
        }
        mySharedPreferences.savedTime(System.nanoTime())
    }

    fun deleteCharacter(id: Int) {
        progressBar.value = true
        var lll : List<CharactersModelItem> = arrayListOf()

        launch {
            val character = CharactersDatabase.invoke(getApplication()).characterDao().getCharacter(id)
            character.let {
                CharactersDatabase.invoke(getApplication()).characterDao().deleteCharacter(it)
            }
            Toast.makeText(getApplication(), "Silindi", Toast.LENGTH_SHORT).show()
            //refresh()  // direct xml'den tetiklerken
        }
    }

    //var listlivedate = MutableLiveData<List<CharactersModelItem>> = MutableLiveData()
    fun searchDatabase(searchQuery: String) {
        launch {
            val dao = CharactersDatabase.invoke(getApplication()).characterDao()

            var list = dao.searchDatabase(searchQuery)
            viewData(list)
        }
    }

      // direct xml'den tetiklerken deneme
//    fun clicked() {
//        Toast.makeText(getApplication(), "clicked", Toast.LENGTH_SHORT).show()
//    }
}