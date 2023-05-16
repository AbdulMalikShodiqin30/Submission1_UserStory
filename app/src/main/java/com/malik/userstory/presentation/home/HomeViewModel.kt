package com.malik.userstory.presentation.home

import androidx.lifecycle.ViewModel
import com.malik.userstory.network.StoryRepository

class HomeViewModel(private val repository: StoryRepository) : ViewModel() {
    fun getStories() = repository.getStories()
}