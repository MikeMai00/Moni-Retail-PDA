package com.example.moniretailpda.data.session

import android.content.Context
import android.content.SharedPreferences



class SessionManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences =  context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object{
        private const val KEY_USER_ID = "user_id"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    fun saveSession(userId: Int){
        sharedPreferences.edit().apply{
            putInt(KEY_USER_ID, userId)
            putBoolean(KEY_IS_LOGGED_IN, true)
            apply()
        }
    }


    fun getUserId(): Int{
        return sharedPreferences.getInt(KEY_USER_ID, -1)
    }

    fun getValue(key: String): Int {
        return sharedPreferences.getInt(key, -1)
    }

    fun isLoggedIn(): Boolean{
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun clearSession() {
        sharedPreferences.edit().apply {
            remove(KEY_USER_ID)
            remove(KEY_IS_LOGGED_IN)
        }
    }
}