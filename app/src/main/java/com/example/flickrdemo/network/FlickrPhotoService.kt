package com.example.flickrdemo.network

import com.example.flickrdemo.model.FlickrResponseModel
import retrofit2.http.GET
import retrofit2.http.Query


interface FlickrPhotoService {


    @GET("services/rest")
    suspend fun getPhotos(
        @Query("method") method: String,
        @Query("api_key") key: String,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("per_page") perPage: Int,
        @Query("format") format: String,
        @Query("page") page: Int,
        @Query("nojsoncallback") noJsonCallback: Int = 1
    ): FlickrResponseModel
}