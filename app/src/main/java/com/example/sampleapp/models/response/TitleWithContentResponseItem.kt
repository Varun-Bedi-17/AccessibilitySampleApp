package com.example.sampleapp.models.response

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "content")
data class TitleWithContentResponseItem(
    val body: String,
    @PrimaryKey val id: Int,
    val title: String,
    val userId: Int
)
