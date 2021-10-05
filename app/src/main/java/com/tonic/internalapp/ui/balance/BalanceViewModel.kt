package com.tonic.internalapp.ui.balance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BalanceViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Balance Fragment"
    }
    val text: LiveData<String> = _text
}