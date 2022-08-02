package com.example.breakingbadproject.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.breakingbadproject.database.CharactersDatabase
import com.example.breakingbadproject.model.CharactersModelItem
import kotlinx.coroutines.launch

class DetailRepository(application: Application) : BaseViewModel(application) {

    var charDetailMutableLiveData = MutableLiveData<CharactersModelItem>()

    @JvmName("getCharDetailMutableLiveData1")
    fun getCharDetailMutableLiveData() : MutableLiveData<CharactersModelItem> {
        return charDetailMutableLiveData
    }

    fun getDetailsFromDB(id: Int) {
        launch {
            val dao = CharactersDatabase.invoke(getApplication()).characterDao()
            val characterDetail = dao.getCharacter(id)
            charDetailMutableLiveData.value = characterDetail
            Toast.makeText(getApplication(), "Data From Database", Toast.LENGTH_SHORT).show()
        }
    }
}