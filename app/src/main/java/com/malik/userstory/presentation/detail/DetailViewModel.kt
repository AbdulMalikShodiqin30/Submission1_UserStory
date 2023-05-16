package com.malik.userstory.presentation.detail

import androidx.lifecycle.ViewModel
import com.malik.userstory.network.StoryRepository

class DetailViewModel(private val repository: StoryRepository) : ViewModel() {
    fun getDetailStory(id: String) = repository.getDetailStory(id)
}