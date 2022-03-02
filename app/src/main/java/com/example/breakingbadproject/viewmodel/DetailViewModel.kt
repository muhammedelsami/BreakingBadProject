package com.example.breakingbadproject.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.breakingbadproject.data.ApiClient
import com.example.breakingbadproject.data.ApiService
import com.example.breakingbadproject.database.CharactersDatabase
import com.example.breakingbadproject.model.CharactersModelItem
import com.example.breakingbadproject.model.DetailModelItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : BaseViewModel(application) {
    lateinit var apiService: ApiService

    val charDetailMutableLiveData = MutableLiveData<CharactersModelItem>()

    fun getDetails(id: Int) {
        getDetailsFromDB(id)
    }

    fun getDetailsFromDB(id: Int) {
        launch {
            val dao = CharactersDatabase.invoke(getApplication()).characterDao()
            val characterDetail = dao.getCharacter(id)
            charDetailMutableLiveData.value = characterDetail
            Toast.makeText(getApplication(), "Data From Database", Toast.LENGTH_SHORT).show()
        }
    }

    fun getCharacter(id : Int) {
        apiService = ApiClient.getClient().create(ApiService::class.java)
        val character = apiService.getDetail(id)

        character.enqueue(object : Callback<List<DetailModelItem>>{
            override fun onResponse(
                call: Call<List<DetailModelItem>>,
                response: Response<List<DetailModelItem>>
            ) {
                // Success
                if (response.isSuccessful) {
                   // charDetailMutableLiveData.value = response.body()?.get(0)
                }
            }

            override fun onFailure(call: Call<List<DetailModelItem>>, t: Throwable) {
                // Error
            }

        })

    }
}