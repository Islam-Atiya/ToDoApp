package com.example.todoapp.ui.home.fragments.taskslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databasee.entity.Task
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.ui.util.getFormatedTime
import java.util.Calendar

class TaskAdapter :RecyclerView.Adapter<TaskAdapter.TaskViewholder>(){
private var tasklist= mutableListOf<Task>()

    fun setTask(task :MutableList<Task>){
        tasklist=task
        notifyDataSetChanged()
    }
class TaskViewholder(val binding:ItemTaskBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(task:Task){
        binding.title.text=task.title
        val calendar=Calendar.getInstance()
        calendar.timeInMillis = task.time!!
        val hr = calendar.get(Calendar.HOUR)
        val mi = calendar.get(Calendar.MINUTE)
        binding.time.text= getFormatedTime(hr,mi)
    }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewholder {
        return TaskViewholder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
return tasklist.size   }

    override fun onBindViewHolder(holder: TaskViewholder, position: Int) {
        val task=tasklist[position]
        holder.bind(task)
    }
}


