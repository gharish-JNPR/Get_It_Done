package com.example.getitdone.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.getitdone.data.GetItDoneDatabase
import com.example.getitdone.data.Task
import com.example.getitdone.data.TaskDao
import com.example.getitdone.databinding.FragmentTasksBinding
import kotlin.concurrent.thread

class TasksFragment: Fragment() , TasksAdapter.TaskItemClickListener{
    private lateinit var binding: FragmentTasksBinding
    private val taskDao: TaskDao by lazy {
        GetItDoneDatabase.getDatabase(requireContext()).getTaskDao()
    }
    private val adapter = TasksAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter= adapter
        fetchAllTasks()
    }

    fun fetchAllTasks() {
        thread {
            val tasks = taskDao.getAllTasks()


            requireActivity().runOnUiThread {
                adapter.setTasks(tasks)
            }
        }
    }

    override fun onTaskUpdated(task: Task) {
        thread{
            taskDao.updateTask(task)
            fetchAllTasks()
        }
    }

    override fun onTaskDeleted(task: Task) {
        thread {
            taskDao.deleteTask(task)
            fetchAllTasks()
        }
    }


}