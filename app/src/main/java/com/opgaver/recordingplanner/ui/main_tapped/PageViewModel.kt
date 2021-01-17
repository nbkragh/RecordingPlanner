package com.opgaver.recordingplanner.ui.main_tapped

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PageViewModel : ViewModel() {

    val _index = MutableLiveData<Int>()


    fun setIndex(index: Int) {
        _index.value = index
    }
}