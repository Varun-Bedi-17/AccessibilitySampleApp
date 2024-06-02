package com.example.accessibilitysampleapp.models.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.accessibilitysampleapp.models.response.TitleWithContentResponseItem


@Database(entities = [TitleWithContentResponseItem::class], version = 1)
abstract class TitleWithContentDatabase : RoomDatabase() {

    abstract fun getDaoFromDB() : TitleWithContentDao

    companion object{
        @Volatile
        private var INSTANCE : TitleWithContentDatabase? = null

        fun getDatabase(context : Context) : TitleWithContentDatabase{
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        TitleWithContentDatabase::class.java,
                        "userDB")
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}