package com.example.sampleapp.models.api

import com.example.sampleapp.models.response.TitleWithContentResponseItem
import retrofit2.http.GET

interface PostTitleContentApi {

    @GET("posts")
    suspend fun getTitleWithContent() : List<TitleWithContentResponseItem>

}