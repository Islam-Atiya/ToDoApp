package com.example.todoapp.ui.util

fun getFormatedTime (hour:Int,minutes:Int):String{
    val minuteString=if (minutes==0)"00" else minutes.toString()


    return "${grtHourIn12(hour)}:$minuteString ${getAmPm(hour)}"

}
fun grtHourIn12(hour:Int):Int{
    return if (hour > 12) hour-12 else if (hour == 0 ) 12 else hour
}
fun getAmPm(hour: Int):String{
return  if (hour<12)"Am" else "Pm"

}