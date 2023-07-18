package com.example.initialdataentry.classes

import android.content.Context
import android.util.Log

class SharedPreference(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("userData", Context.MODE_PRIVATE)
    fun sharedContains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    fun sharedSave(key: String, data: String) {
        val editor = sharedPreferences?.edit()
        editor?.putString(key, data)
        editor?.apply()
    }

    fun sharedRemove(key: String) {
        val editor = sharedPreferences?.edit()
        editor?.remove(key)
        editor?.apply()
    }

    fun getShared(key: String): String {
            return sharedPreferences?.getString(key, "Нет данных").toString()
    }
}