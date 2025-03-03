package com.example.getitdone.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getitdone.GetItDoneApplication.Companion.taskDao
import com.example.getitdone.data.Task
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){


    fun createTask(title : String,description: String?){
        val task = Task(
            title = title,
            description = description
        )
        viewModelScope.launch {
            taskDao.createTask(task)
        }

    }

}