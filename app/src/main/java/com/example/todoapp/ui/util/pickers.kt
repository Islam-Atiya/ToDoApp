package com.example.todoapp.ui.util

import android.app.DatePickerDialog
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

fun showDatePikerDialog(context: Context,callback:(String,Calendar)->Unit){
    val dialog=DatePickerDialog(context)
    dialog.datePicker.minDate=System.currentTimeMillis()
    dialog.setOnDateSetListener{piker ,year,month,day->

        val calendar=Calendar.getInstance()
        calendar.set(Calendar.YEAR,year)
        calendar.set(Calendar.MONTH,month)
        calendar.set(Calendar.DAY_OF_MONTH,day)
        calendar.clearTime()
        callback("$day/${month+1}/$year",calendar)

    }
    dialog.show()

}
fun showTimePikerDialog(hour:Int,minute:Int,title:String,fragment:FragmentManager,callback:(Int,Int)->Unit){
    val picker=MaterialTimePicker.Builder()

        .setTimeFormat(TimeFormat.CLOCK_12H)
        .setHour(hour)
        .setMinute(minute)
        .setTitleText(title)
        .build()
    picker.show(fragment,"")
    picker.addOnPositiveButtonClickListener{
        callback(picker.hour,picker.minute)
    }
    picker.addOnNegativeButtonClickListener{
        picker.dismiss()
    }
}