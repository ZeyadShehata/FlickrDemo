package com.example.flickrdemo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.flickrdemo.ui.images_list.PhotoPagingSource


class ImagesListViewModel(lat: String, lon: String) : ViewModel() {
    private var _showLoadingLiveData = MutableLiveData<Boolean>()
    val showLoadingLiveData: LiveData<Boolean>
        get() = _showLoadingLiveData
    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 15)
    ) {
        PhotoPagingSource(lat, lon)

    }.flow.cachedIn(viewModelScope)

    fun setResponseSent(success:Boolean) {
        _showLoadingLiveData.value = success
    }

}