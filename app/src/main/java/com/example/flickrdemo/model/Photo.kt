package com.example.flickrdemo.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Photo(
    @SerializedName("id")
    val id: String,
    @Json(name = "owner")
    val owner: String,
    val secret: String,
    val server: String,
    val farm: String,
    val title: String,
    val ispublic: Int,
    val isfriend: Int,
    val isfamily: Int,

    ) {

    override fun toString(): String {

        return "https://live.staticflickr.com/${server}/${id}_${secret}_q.jpg"
    }

}