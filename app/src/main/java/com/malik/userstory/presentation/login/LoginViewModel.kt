package com.malik.userstory.presentation.login

import androidx.lifecycle.ViewModel
import com.malik.userstory.network.StoryRepository

class LoginViewModel(private val repository: StoryRepository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)
}