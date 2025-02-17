package com.example.todo.ui.home.fragments.addtask

import android.app.DatePickerDialog
import android.app.TimePickerDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.databasee.MyDatabase
import com.example.todoapp.databasee.dao.TaskDao
import com.example.todoapp.databasee.entity.Task
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.ui.util.clearDate
import com.example.todoapp.ui.util.clearSeconds
import com.example.todoapp.ui.util.clearTime
import com.example.todoapp.ui.util.getFormatedTime
import com.example.todoapp.ui.util.showDatePikerDialog
import com.example.todoapp.ui.util.showTimePikerDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddTaskFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddTaskBinding
    lateinit var dao :TaskDao
    private  var dateCalender= Calendar.getInstance()
    private  var timeCalender= Calendar.getInstance()
     var  onTaskAdded:OnTaskAdded?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dao=MyDatabase.getInstance().taskDao()
        onSelectDateClick()
        onSelectTimeClick()
        onAddTaskClick()

    }

    private fun onAddTaskClick() {
        binding.addTaskBtn.setOnClickListener{
        if (!validateInput()){
            return@setOnClickListener}
           val tasks= createTask()
            dao.insertNewTask(tasks)
            onTaskAdded?.onAddTask(tasks)
            dismiss()

        }
    }

    private fun createTask():Task {
        return Task(title = binding.title.text.toString(), date = dateCalender.timeInMillis, time = timeCalender.timeInMillis
        , description = binding.description.text.toString())

    }

    private fun onSelectTimeClick() {
        binding.selectTimeTv.setOnClickListener{
            val calendar=Calendar.getInstance()
            showTimePikerDialog(calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),"Select Time",childFragmentManager){hour,minute->
                binding.selectTimeTv.text=  getFormatedTime(hour,minute)
                timeCalender.set(Calendar.HOUR,hour)
                timeCalender.set(Calendar.MINUTE,minute)
                timeCalender.clearDate()
                timeCalender.clearSeconds()

            }

        }
    }

    private fun onSelectDateClick() {
        binding.selectDateTv.setOnClickListener{
            showDatePikerDialog(requireContext()) { date, calender ->
                binding.selectDateTv.text = date
                dateCalender.set(Calendar.YEAR, calender.get(Calendar.YEAR))
                dateCalender.set(Calendar.MONTH, calender.get(Calendar.MONTH))
                dateCalender.set(Calendar.DAY_OF_MONTH, calender.get(Calendar.DAY_OF_MONTH))
                dateCalender.clearTime()

            }
        }

    }

    fun validateInput():Boolean{
        var isvaled=true
        if (binding.title.text.isNullOrBlank()){
            isvaled=false
            binding.titleTil.error="title required"
        }
        if (binding.selectDateTv.text.isNullOrBlank()) {
            isvaled = false
            binding.selectDateTil.error = "date required"
        }
        if (binding.selectTimeTv.text.isNullOrBlank()) {
            isvaled = false
            binding.selectTimeTil.error = "time required"
        }
        return isvaled
    }


    fun interface OnTaskAdded{
        fun onAddTask(task: Task)
    }
}