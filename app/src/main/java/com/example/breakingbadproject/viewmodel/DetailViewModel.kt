package com.example.breakingbadproject.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.breakingbadproject.data.ApiClient
import com.example.breakingbadproject.data.ApiService
import com.example.breakingbadproject.model.DetailModelItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    lateinit var apiService: ApiService

    val charDetailMutableLiveData = MutableLiveData<DetailModelItem>()

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
                    charDetailMutableLiveData.value = response.body()?.get(0)
                }
            }

            override fun onFailure(call: Call<List<DetailModelItem>>, t: Throwable) {
                // Error
            }

        })

    }
}