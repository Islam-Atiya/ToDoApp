package com.example.todoapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import com.example.todoapp.databasee.MyDatabase
import com.example.todoapp.ui.util.applyModeChange
import com.example.todoapp.ui.util.constans

class MyApp:Application() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        MyDatabase.init(this)
        setNighMode()
    }

    private fun setNighMode() {
        sharedPreferences = getSharedPreferences(constans.sp_Name, Context.MODE_PRIVATE)
        val isDark = sharedPreferences.getBoolean(constans.isDarkModeKey, getDeviceModeState())
        applyModeChange(isDark)
    }

    private fun getDeviceModeState(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }
}