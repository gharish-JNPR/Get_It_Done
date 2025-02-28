package com.example.getitdone.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 3)
abstract class GetItDoneDatabase : RoomDatabase() {
    abstract fun getTaskDao() : TaskDao

    companion object{

        @Volatile
        private var DATABASE_INSTANCE : GetItDoneDatabase?= null

        fun getDatabase(context: Context): GetItDoneDatabase {
            return DATABASE_INSTANCE ?: synchronized(this){
                val database = Room.databaseBuilder(
                    context,
                    GetItDoneDatabase::class.java,
                    "get-it-done-database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                DATABASE_INSTANCE = database
                return database
            }

            //if we don't have a database make one, else return existing one
        }
    }

}