package com.example.flickrdemo.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


val urll = "https://www.flickr.com/"


private val retrofit =
    Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).baseUrl(urll).build()

interface FlickrPhotoService {


    @GET("services/rest")
    suspend fun getPhotos(
        @Query("method") method: String,
        @Query("api_key") key: String,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("per_page") perPage: Int,
        @Query("format") format: String,
        @Query("page") page: Int
    ): String
}

object FlickrAPI {
    val retrofitService: FlickrPhotoService by lazy { retrofit.create(FlickrPhotoService::class.java) }
}

