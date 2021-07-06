package com.example.flickrdemo.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class MapViewModel : ViewModel() {
    private var _lat = MutableLiveData<String>()
    val lat: LiveData<String>
        get() = _lat
    private var _lon = MutableLiveData<String>()
    val lon: LiveData<String>
        get() = _lon
    private var _navigateToResults = MutableLiveData<Boolean?>()
    val navigateToResults: LiveData<Boolean?>
        get() = _navigateToResults
    private var latlon: LatLng? = null
    fun doneSearching() {

        _lat.value = null
        _lon.value = null
        _navigateToResults.value = false
    }

    fun saveCoords(lati: String, long: String) {
        _lat.value = lati
        _lon.value = long
        latlon = LatLng(lati.toDouble(), long.toDouble())
    }

    fun navigateToSearch() {
        _navigateToResults.value = true
    }

    fun returnLatLon(): LatLng {
        return latlon as LatLng
    }

    fun checkLatLon(): Boolean {
        if (latlon != null) {
            return true
        } else {
            return false
        }
    }
}