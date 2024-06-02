package com.example.accessibilitysampleapp.models.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PostTitleContentHelper {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    fun getInstance() : Retrofit {
        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}