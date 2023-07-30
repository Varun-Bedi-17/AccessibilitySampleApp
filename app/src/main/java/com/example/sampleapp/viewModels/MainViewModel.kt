package com.example.sampleapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.models.repository.TitleWithContentRepo
import com.example.sampleapp.models.response.TitleWithContentResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class MainViewModel(private val titleWithContentRepo: TitleWithContentRepo) : ViewModel() {

    suspend fun addDataFromViewModelToDatabase() {
        viewModelScope.async(Dispatchers.IO){
            titleWithContentRepo.addDataFromApiToDatabase()
        }.await()
    }
    fun getDataFromDatabase(): List<TitleWithContentResponseItem> {
        return titleWithContentRepo.getDataFromDatabase()
    }
}