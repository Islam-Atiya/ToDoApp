package com.example.todoapp.ui.home.fragments.edit_task

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todoapp.R
import com.example.todoapp.databasee.MyDatabase
import com.example.todoapp.databasee.dao.TaskDao
import com.example.todoapp.databasee.entity.Task
import com.example.todoapp.databinding.ActivityEditTaskBinding
import com.example.todoapp.ui.util.clearDate
import com.example.todoapp.ui.util.clearSeconds
import com.example.todoapp.ui.util.clearTime
import com.example.todoapp.ui.util.constans
import com.example.todoapp.ui.util.getFormatedTime
import com.example.todoapp.ui.util.showDatePikerDialog
import com.example.todoapp.ui.util.showTimePikerDialog
import java.util.Calendar

class EditTaskActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditTaskBinding
    lateinit var intienttask: Task
    lateinit var dao: TaskDao

    private var dateCalender = Calendar.getInstance()
    private var timeCalender = Calendar.getInstance()

    lateinit var newtask: Task
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intienttask =
            IntentCompat.getParcelableExtra(intent, constans.TASK_KEY, Task::class.java) as Task
        newtask = intienttask.copy()
        dao = MyDatabase.getInstance().taskDao()

        setupToolBar()
        intiViews()
        onSelectTimeClick()
        onSelectDateClick()
        onSaveBtnClick()
    }

    private fun onSaveBtnClick() {
        binding.contact.btnSave.setOnClickListener {
            if (!validateInput()) {
                return@setOnClickListener
            } else {
                updateTaskAndfinish()
            }
        }
    }

    private fun updateTaskAndfinish() {
        newtask.apply {
            title = binding.contact.title.text.toString()
            description = binding.contact.description.text.toString()
        }
        dao.updateTask(newtask)
        finish()
    }

    fun validateInput(): Boolean {
        var isvaled = true
        if (binding.title.text.isNullOrBlank()) {
            isvaled = false
            binding.contact.titleTil.error = "title required"
        } else {
            binding.contact.titleTil.error = null
        }


        return isvaled
    }

    private fun onSelectTimeClick() {
        binding.contact.selectTimeTv.setOnClickListener {
            val calendar = Calendar.getInstance()
            showTimePikerDialog(
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE), "Select Time", supportFragmentManager
            ) { hour, minute ->
                binding.contact.selectTimeTv.text = getFormatedTime(hour, minute)
                timeCalender.set(Calendar.HOUR, hour)
                timeCalender.set(Calendar.MINUTE, minute)
                timeCalender.clearDate()
                timeCalender.clearSeconds()

                newtask.time = timeCalender.timeInMillis
            }

        }
    }

    private fun onSelectDateClick() {
        binding.contact.selectDateTv.setOnClickListener {
            showDatePikerDialog(this) { date, calender ->
                binding.contact.selectDateTv.text = date
                dateCalender.set(Calendar.YEAR, calender.get(Calendar.YEAR))
                dateCalender.set(Calendar.MONTH, calender.get(Calendar.MONTH))
                dateCalender.set(Calendar.DAY_OF_MONTH, calender.get(Calendar.DAY_OF_MONTH))
                dateCalender.clearTime()

                newtask.date = dateCalender.timeInMillis
            }
        }

    }

    private fun intiViews() {
        binding.contact.title.setText(intienttask.title)
        binding.contact.description.setText(intienttask.description)


        val calendar = Calendar.getInstance()
        calendar.timeInMillis = intienttask.time
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        binding.contact.selectDateTv.text = "$day/${month - 1}/$year"


        calendar.timeInMillis = intienttask.date
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        binding.contact.selectTimeTv.text = getFormatedTime(hour, minute)
    }

    private fun setupToolBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
//        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                finish()
//            }
//
//        })
    }
}