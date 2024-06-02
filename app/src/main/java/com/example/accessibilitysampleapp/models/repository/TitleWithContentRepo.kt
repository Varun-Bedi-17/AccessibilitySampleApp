package com.example.accessibilitysampleapp.models.repository

import android.util.Log
import com.example.accessibilitysampleapp.TAG
import com.example.accessibilitysampleapp.models.api.PostTitleContentApi
import com.example.accessibilitysampleapp.models.response.TitleWithContentResponseItem
import com.example.accessibilitysampleapp.models.roomDatabase.TitleWithContentDatabase
import java.io.IOException

class TitleWithContentRepo(private val postTitleContentApi: PostTitleContentApi, private val titleWithContentDatabase: TitleWithContentDatabase) {

    suspend fun addDataFromApiToDatabase(){
        try {
            val itemList = postTitleContentApi.getTitleWithContent()
            titleWithContentDatabase.getDaoFromDB().insertDataFromDao(itemList)
        }catch (e:Exception){
            if (e is IOException) {
                // Mostly network, server and timeout error occurs
                Log.i(TAG, "Network or server error ")
            }else{
                Log.i(TAG, e.localizedMessage?.toString() ?: "Error in calling api")
            }
        }

    }

    fun getDataFromDatabase(): List<TitleWithContentResponseItem> {
        return titleWithContentDatabase.getDaoFromDB().getDataFromDao()
    }
}