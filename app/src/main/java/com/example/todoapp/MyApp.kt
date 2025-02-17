package com.example.todoapp

import android.app.Application
import com.example.todoapp.databasee.MyDatabase

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        MyDatabase.init(this)
    }
}