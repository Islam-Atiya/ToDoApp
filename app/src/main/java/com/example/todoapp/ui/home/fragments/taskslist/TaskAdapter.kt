package com.example.todoapp.ui.home.fragments.taskslist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databasee.entity.Task
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.ui.util.getFormatedTime
import com.zerobranch.layout.SwipeLayout
import com.zerobranch.layout.SwipeLayout.SwipeActionsListener
import java.util.Calendar

class TaskAdapter :RecyclerView.Adapter<TaskAdapter.TaskViewholder>(){
private var tasklist= mutableListOf<Task>()

    fun setTask(task :MutableList<Task>){
        tasklist=task
        notifyDataSetChanged()
    }

    fun deleteTask(position: Int) {
        if (position in tasklist.indices) {
            tasklist.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, tasklist.size - position)
        }


    }

    fun updateTask(position: Int, task: Task) {
        if (position in tasklist.indices) {
            tasklist[position] = task
            notifyItemChanged(position)
        }

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

    fun bindIsDoneStatus(isDone: Boolean) {
        if (isDone) {
            binding.draggingBar.setImageResource(R.drawable.dragging_bar_done)
            binding.title.setTextColor(Color.GREEN)
            binding.btnTaskIsDone.setBackgroundResource(R.drawable.done)
        } else {
            binding.draggingBar.setImageResource(R.drawable.dragging_bar)
            val blue = ContextCompat.getColor(itemView.context, R.color.blue)
            binding.title.setTextColor(blue)
            binding.btnTaskIsDone.setBackgroundResource(R.drawable.check_mark)
        }


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
        holder.bindIsDoneStatus(task.isDone)
        holder.binding.leftView.isClickable = true



        onDeleteBtnClickListener?.let {


            holder.binding.swipeLayout.setOnActionsListener(object : SwipeActionsListener {
                override fun onOpen(direction: Int, isContinuous: Boolean) {
                    if (direction == SwipeLayout.RIGHT) {
                        holder.binding.leftView.setOnClickListener {
                            onDeleteBtnClickListener?.onclick(holder.adapterPosition, task)
                        }
                    }

                }

                override fun onClose() {
                    holder.binding.leftView.isClickable = false
                }

            })
        }
        onDoneBtnClickListener?.let {
            holder.binding.btnTaskIsDone.setOnClickListener {
                onDoneBtnClickListener?.onclick(position, task)
                //////////////////////////?????
            }
        }

        onItemClickListener?.let {
            holder.binding.dragItem.setOnClickListener {
                onItemClickListener?.onclick(position, task)
            }
        }

    }

    var onDeleteBtnClickListener: onTaskClickListener? = null
    var onDoneBtnClickListener: onTaskClickListener? = null
    var onItemClickListener: onTaskClickListener? = null


    fun interface onTaskClickListener {
        fun onclick(position: Int, task: Task)
    }
}



