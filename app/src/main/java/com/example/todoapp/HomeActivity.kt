package com.example.todoapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todo.ui.home.fragments.addtask.AddTaskFragment
import com.example.todo.ui.home.fragments.settings.SettingsFragment
import com.example.todo.ui.home.fragments.taskslist.TasksFragment
import com.example.todoapp.databasee.entity.Task
import com.example.todoapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var bindind :ActivityHomeBinding
        @SuppressLint("SuspiciousIndentation")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            bindind=ActivityHomeBinding.inflate(layoutInflater)
                setContentView(bindind.root)

                setNavgetion()
                setOnFabClick()


        }

    private fun setNavgetion() {
    bindind.bottomNavigationView.setOnItemSelectedListener { menuItem->
        if (menuItem.itemId==R.id.tasks){
            showFragment(TasksFragment())
            bindind.title.text=getString(R.string.to_do_list)
        }else if (menuItem.itemId==R.id.settings){
            showFragment(SettingsFragment())
            bindind.title.text=getString(R.string.settings)
        }
        true
    }

    }

    private fun showFragment(fragment: Fragment) {
supportFragmentManager.beginTransaction()
    .replace(R.id.fragment_container,fragment)
    .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
    .commit()
    }

   private fun setOnFabClick() {
       bindind.fabAddTask.setOnClickListener{
           val bottomsheet=AddTaskFragment()
           bottomsheet.show(supportFragmentManager,"")
           bottomsheet.onTaskAdded=AddTaskFragment.OnTaskAdded { task: Task ->

           }

       }
    }


}