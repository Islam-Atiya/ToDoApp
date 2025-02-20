package com.example.todoapp.databasee.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Task (
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    @ColumnInfo
    var title: String,
    @ColumnInfo
    var description: String?=null,
    @ColumnInfo
    var date: Long,
    @ColumnInfo
    var time: Long,
    @ColumnInfo
    var isDone: Boolean = false,

    ) : Parcelable
