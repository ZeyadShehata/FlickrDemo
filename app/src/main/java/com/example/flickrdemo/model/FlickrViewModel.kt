package com.example.flickrdemo.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.flickrdemo.network.PhotoPagingSource


class FlickrViewModel(lat: String, lon: String) : ViewModel() {
    private var _responseSent = MutableLiveData<Boolean>()
    val responseSent: LiveData<Boolean>
        get() = _responseSent
    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 15)
    ) {
        PhotoPagingSource(lat, lon)

    }.flow.cachedIn(viewModelScope)

    fun setResponseSent(success:Boolean) {
        _responseSent.value = success
    }

}