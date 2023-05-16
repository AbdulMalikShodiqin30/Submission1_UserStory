package com.malik.userstory.di

import android.content.Context
import com.malik.userstory.network.StoryRepository
import com.malik.userstory.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService(context)
        return StoryRepository.getInstance(apiService)
    }
}