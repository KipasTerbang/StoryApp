package com.dicoding.storyapp.core.data.source.remote.request

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

data class NewStoryRequest(
    @Part val description: RequestBody,
    @Part val photo: MultipartBody.Part,
    val lat: Double? = null,
    val lon: Double? = null
)