package com.example.breakingbadproject.data

import com.example.breakingbadproject.model.CharactersModelItem
import com.example.breakingbadproject.model.DetailModelItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    //for all characters
    @GET("characters")
    fun characterList(): Call<List<CharactersModelItem>>

    // for one character
    @GET("characters/{id}")
    fun getDetail(@Path("id") id: Int?) : Call<List<DetailModelItem>>

}