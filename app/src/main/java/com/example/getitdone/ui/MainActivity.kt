package com.example.getitdone.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.getitdone.data.Task
import com.example.getitdone.databinding.ActivityMainBinding
import com.example.getitdone.databinding.DialogAddTaskBinding
import com.example.getitdone.ui.tasks.TasksFragment
import com.example.getitdone.util.InputValidator
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel : MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private val tasksFragment: TasksFragment = TasksFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            pager.adapter = PagerAdapter(this@MainActivity)

            TabLayoutMediator(tabs, pager) { tab, _ ->
                tab.text = "Tasks"
            }.attach()
            fab.setOnClickListener { showAddTaskDialog() }

            setContentView(root)
        }

        CoroutineScope(Dispatchers.Main).launch {
            startTitleTicker()
        }



    }

    suspend fun startTitleTicker(){
        var count=1
        while(true){
            delay(1000)
            binding.toolbar.title = "seconds $count"
            count++
        }
    }

    private fun showAddTaskDialog() {
        DialogAddTaskBinding.inflate(layoutInflater).apply {
            val dialog = BottomSheetDialog(this@MainActivity)
            dialog.setContentView(root)

            buttonSave.isEnabled = false

            editTextTaskTitle.addTextChangedListener {
                buttonSave.isEnabled = InputValidator.validInput(it.toString())
            }

            buttonShowDetails.setOnClickListener {
                editTextTaskDetails.visibility =
                    if (editTextTaskDetails.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }

            buttonSave.setOnClickListener {
                val task = Task(
                    title = editTextTaskTitle.text.toString(),
                    description = editTextTaskDetails.text.toString()
                )
                viewModel.createTask(
                    title = editTextTaskTitle.text.toString(),
                    description =  editTextTaskDetails.text.toString())
                dialog.dismiss()
                tasksFragment.fetchAllTasks()
            }
            dialog.show()
        }
    }


    private inner class PagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 1

        override fun createFragment(position: Int): Fragment {
            return tasksFragment
        }
    }
}