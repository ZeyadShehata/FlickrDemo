package com.example.flickrdemo.ui.images_list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.flickrdemo.model.Photo
import com.example.flickrdemo.network.FlickrAPI
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

class PhotoPagingSource(private val lat: String, private val lon: String) :
    PagingSource<Int, Photo>() {
    private val method = "flickr.photos.search"
    private val key = "da90c306934d086007d9bdb7a3b8c663"
    private val resType = "json"
    private val perPage = 15
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)


        }
    }


    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, Photo> {
        try {
            val nextPageNumber = params.key ?: 1
            val gson = Gson()
            val response = FlickrAPI.retrofitService.getPhotos(
                method,
                key,
                lat,
                lon,
                perPage,
                resType,
                nextPageNumber
            )
            val formattedResponse = response


            return LoadResult.Page(
                data = response.photos.photo,
                prevKey = null,
                nextKey = (nextPageNumber) + 1
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }

    }
}