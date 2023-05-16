package com.malik.userstory.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.malik.userstory.di.Injection
import com.malik.userstory.presentation.detail.DetailViewModel
import com.malik.userstory.presentation.home.HomeViewModel
import com.malik.userstory.presentation.login.LoginViewModel
import com.malik.userstory.presentation.newstory.NewStoryViewModel
import com.malik.userstory.presentation.register.RegisterViewModel

class ViewModelFactory(private val context: Context) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) RegisterViewModel(
            Injection.provideRepository(context)
        ) as T
        else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) LoginViewModel(
            Injection.provideRepository(context)
        ) as T
        else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) HomeViewModel(
            Injection.provideRepository(context)
        ) as T
        else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) DetailViewModel(
            Injection.provideRepository(context)
        ) as T
        else if (modelClass.isAssignableFrom(NewStoryViewModel::class.java)) NewStoryViewModel(
            Injection.provideRepository(context)
        ) as T
        else throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}