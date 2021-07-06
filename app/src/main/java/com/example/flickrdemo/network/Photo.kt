package com.example.flickrdemo.network

data class Photo(
    val id: String,
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