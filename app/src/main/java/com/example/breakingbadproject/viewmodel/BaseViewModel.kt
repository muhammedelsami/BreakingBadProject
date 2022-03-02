package com.example.breakingbadproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private val jop = Job()
    override val coroutineContext: CoroutineContext
        get() = jop + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        jop.cancel()
    }
}