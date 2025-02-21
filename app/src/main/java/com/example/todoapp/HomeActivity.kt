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
    private var tasksFragment: TasksFragment? = null
    private var currentFragment: String? = null
        @SuppressLint("SuspiciousIndentation")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            bindind=ActivityHomeBinding.inflate(layoutInflater)
                setContentView(bindind.root)
            tasksFragment =
                supportFragmentManager.findFragmentByTag("TASKS_FRAGMENT") as? TasksFragment
                    ?: TasksFragment()
                setNavgetion()
                setOnFabClick()

            if (savedInstanceState != null) {
                currentFragment = savedInstanceState.getString("currentFragment")
                if (currentFragment != null) {
                    val fragment = supportFragmentManager.findFragmentByTag(currentFragment)
                    if (fragment != null) {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .replace(R.id.fragment_container, fragment, currentFragment)

                            .commit()

                    }
                }
            } else {
                bindind.bottomNavigationView.selectedItemId = R.id.tasks

            }
        }

    private fun setNavgetion() {
    bindind.bottomNavigationView.setOnItemSelectedListener { menuItem->
        if (menuItem.itemId==R.id.tasks){
            showFragment(tasksFragment!!, "TASKS_FRAGMENT")
            bindind.title.text=getString(R.string.to_do_list)
        }else if (menuItem.itemId==R.id.settings){
            showFragment(SettingsFragment(), "SITTING_FRAGMENT")
            bindind.title.text=getString(R.string.settings)
        }
        true
    }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentFragment", currentFragment)
    }
    private fun showFragment(fragment: Fragment, tag: String) {
        currentFragment = tag
supportFragmentManager.beginTransaction()
    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
    .replace(R.id.fragment_container, fragment, tag)
    .commit()
    }

   private fun setOnFabClick() {
       bindind.fabAddTask.setOnClickListener{
           val bottomsheet=AddTaskFragment()
           bottomsheet.show(supportFragmentManager,"")
           bottomsheet.onTaskAdded=AddTaskFragment.OnTaskAdded { task: Task ->

               tasksFragment?.lodAllTaskOfDate(task.date!!)

           }

       }
    }


}