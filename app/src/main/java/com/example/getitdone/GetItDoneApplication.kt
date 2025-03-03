package com.example.getitdone

import android.app.Application
import com.example.getitdone.data.GetItDoneDatabase
import com.example.getitdone.data.TaskDao

class GetItDoneApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        database = GetItDoneDatabase.getDatabase(this)
        taskDao = database.getTaskDao()
    }

    companion object{

        lateinit var database : GetItDoneDatabase
        lateinit var taskDao: TaskDao

    }

}