package com.malik.userstory.presentation.register

import androidx.lifecycle.ViewModel
import com.malik.userstory.network.StoryRepository

class RegisterViewModel(private val repository: StoryRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) =
        repository.register(name, email, password)
}