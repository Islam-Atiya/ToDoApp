package com.example.todoapp.databasee

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.databasee.dao.TaskDao
import com.example.todoapp.databasee.entity.Task

@Database(entities = [Task::class],version=1 , exportSchema = true ,)
abstract class MyDatabase :RoomDatabase( ){

    abstract fun taskDao():TaskDao
    companion object{
        private val DataBase_name="tasks"
        private var myDatabase:MyDatabase?=null

        fun init(applicationContext: Context){
            if (myDatabase==null){
                myDatabase= Room.databaseBuilder(
                    applicationContext,MyDatabase::class.java, DataBase_name)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }

        fun getInstance():MyDatabase{

            return myDatabase!!
        }
    }
}