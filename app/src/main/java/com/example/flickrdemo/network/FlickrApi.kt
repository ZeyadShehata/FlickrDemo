package com.example.flickrdemo.network

import com.example.flickrdemo.utils.FLICKR_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
private val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()


private val retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(FLICKR_BASE_URL)
        .client(okHttpClient)
        .build()

object FlickrAPI {
    val retrofitService: FlickrPhotoService by lazy { retrofit.create(FlickrPhotoService::class.java) }
}

