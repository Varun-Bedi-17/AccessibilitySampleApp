package com.example.accessibilitysampleapp.models.roomDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.accessibilitysampleapp.models.response.TitleWithContentResponseItem


@Dao
interface TitleWithContentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataFromDao(data : List<TitleWithContentResponseItem>)

    @Query("Select * from content")
    fun getDataFromDao() : List<TitleWithContentResponseItem>
}