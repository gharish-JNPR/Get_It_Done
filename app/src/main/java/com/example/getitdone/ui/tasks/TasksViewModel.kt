package com.example.getitdone.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getitdone.GetItDoneApplication.Companion.taskDao
import com.example.getitdone.data.Task
import kotlinx.coroutines.launch

class TasksViewModel : ViewModel() {
//    fun fetchTasks(callback : (List<Task>) -> Unit){
//
//        viewModelScope.launch {
//            val tasks = taskDao.getAllTasks()
//            callback(tasks)
//        }
//
//    }

    suspend fun fetchTasks(): List<Task>{
        val tasks = taskDao.getAllTasks()
        return tasks
    }

    fun updateTask(task: Task){
        viewModelScope.launch {
            taskDao.updateTask(task)
        }
    }

    fun deleteTask(task: Task){

        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }
}