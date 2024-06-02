package com.example.accessibilitysampleapp.models.api

import com.example.accessibilitysampleapp.models.response.TitleWithContentResponseItem
import retrofit2.http.GET

interface PostTitleContentApi {

    @GET("posts")
    suspend fun getTitleWithContent() : List<TitleWithContentResponseItem>

}