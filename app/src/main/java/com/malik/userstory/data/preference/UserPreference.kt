package com.malik.userstory.data.preference

import android.content.Context

internal class UserPreferences(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor = preferences.edit()
    fun saveToken(token: String) {
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String = preferences.getString(USER_TOKEN, "").toString()

    fun removeToken() {
        editor.remove(USER_TOKEN)
        editor.apply()
    }

    companion object {
        private const val PREFS_NAME = "user_preference"
        private const val USER_TOKEN = "user_token"
    }
}