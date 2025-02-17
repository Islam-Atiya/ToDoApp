package com.example.todoapp.databasee.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.databasee.entity.Task

@Dao
interface TaskDao {
    @Insert
    fun insertNewTask(task: Task)
    @Delete
    fun deleteTask(task: Task )
    @Update
    fun updateTask(task: Task )

    @Query("select * from task")
    fun getAllTask():List<Task>

    @Query("select * from task where date=:paramDate ")
    fun getAllTaskByDate(paramDate:Long):List<Task>

    @Query("select * from task where date=:id ")
    fun getTaskById(id:Int):Task?

    @Query("select * from task where isDone=0 ")
    fun getUnCompletedTask():List<Task>
}