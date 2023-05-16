package com.malik.userstory.presentation.newstory

import androidx.lifecycle.ViewModel
import com.malik.userstory.network.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class NewStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    fun uploadStory(imageMultiPart: MultipartBody.Part, description: RequestBody) =
        repository.uploadStory(imageMultiPart, description)
}