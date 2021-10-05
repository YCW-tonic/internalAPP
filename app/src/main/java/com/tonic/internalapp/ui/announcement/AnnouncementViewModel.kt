package com.tonic.internalapp.ui.announcement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AnnouncementViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Announcement Fragment"
    }
    val text: LiveData<String> = _text
}