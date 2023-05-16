package com.malik.userstory.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.malik.userstory.data.response.DetailStoryResponse
import com.malik.userstory.data.response.LoginResponse
import com.malik.userstory.data.response.RegisterResponse
import com.malik.userstory.data.response.StoriesResponse
import com.malik.userstory.data.response.StoryUploadResponse
import com.malik.userstory.utils.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository private constructor(private val apiService: ApiService) {
    fun uploadStory(
        imageMultiPart: MultipartBody.Part,
        description: RequestBody
    ): LiveData<Result<StoryUploadResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.uploadStory(imageMultiPart, description)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun getDetailStory(id: String): LiveData<Result<DetailStoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailStory(id)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun getStories(): LiveData<Result<StoriesResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getStories()
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(name, email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            apiService: ApiService,
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService)
            }.also { instance = it }
    }
}