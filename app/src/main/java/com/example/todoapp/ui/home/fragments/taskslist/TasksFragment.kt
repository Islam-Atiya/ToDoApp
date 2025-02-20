package com.example.todo.ui.home.fragments.taskslist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.databasee.MyDatabase
import com.example.todoapp.databasee.dao.TaskDao
import com.example.todoapp.databinding.FragmentTasksBinding
import com.example.todoapp.ui.home.fragments.edit_task.EditTaskActivity
import com.example.todoapp.ui.home.fragments.taskslist.TaskAdapter
import com.example.todoapp.ui.util.clearTime
import com.example.todoapp.ui.util.constans
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.time.LocalDateTime
import java.util.Calendar


class   TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!
private val adapter=TaskAdapter()
    lateinit var dao: TaskDao
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dao=MyDatabase.getInstance().taskDao()
        initRecyclerView()
        initCalendarView()
    }

    private fun initCalendarView() {
        binding.calendarView.selectedDate= CalendarDay.today()
        binding.calendarView.setOnDateChangedListener { _, date , selected ->
            val calendar=Calendar.getInstance()
            calendar.set(Calendar.YEAR,date.year)
            calendar.set(Calendar.MONTH,date.month-1)
            calendar.set(Calendar.DAY_OF_MONTH,date.day)
            calendar.clearTime()
            if (selected){
                val tasks=dao.getAllTaskByDate(calendar.timeInMillis).toMutableList()
                adapter.setTask(tasks)
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun initRecyclerView() {
    binding.rvTasks.adapter=adapter
        adapter.onDeleteBtnClickListener = TaskAdapter.onTaskClickListener { position, task ->

            dao.deleteTask(task)
            adapter.deleteTask(position)

        }
        adapter.onDoneBtnClickListener = TaskAdapter.onTaskClickListener { position, task ->
            task.isDone = !task.isDone
            dao.updateTask(task)
            adapter.updateTask(position, task)
        }

        adapter.onItemClickListener = TaskAdapter.onTaskClickListener { position, task ->
            val intent = Intent(requireContext(), EditTaskActivity::class.java)
            intent.putExtra(constans.TASK_KEY, task)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        lodAllTaskOfDate(getSelectedDate().timeInMillis)
    }

    fun lodAllTaskOfDate(date: Long) {
            val task =dao.getAllTaskByDate(date).toMutableList()
        adapter.setTask(task)
    }

    private fun getSelectedDate():Calendar{
        val calendar=Calendar.getInstance()
        binding.calendarView.selectedDate?.let {
            calendar.set(Calendar.YEAR,it.year)
            calendar.set(Calendar.MONTH,it.month-1)
            calendar.set(Calendar.DAY_OF_MONTH,it.day)
        }
        calendar.clearTime()
    return calendar
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter.onDoneBtnClickListener = null
        adapter.onDeleteBtnClickListener = null
        adapter.onItemClickListener = null
    }

}
