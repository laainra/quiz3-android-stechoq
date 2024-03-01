package com.lailaar.quiz3.remote

import com.lailaar.quiz3.data.model.Photo
import retrofit2.http.GET

interface ApiService {
    @GET("/photos")
    suspend fun getPhotos(): List<Photo>
}
