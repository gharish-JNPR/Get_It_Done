package com.example.getitdone.ui.tasks

import android.annotation.SuppressLint
import android.graphics.Paint
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getitdone.data.Task
import com.example.getitdone.databinding.ItemTaskBinding
import com.google.android.material.checkbox.MaterialCheckBox

class TasksAdapter(private val listener: TaskUpdatedListener) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    private var tasks: List<Task> = listOf()

    override fun getItemCount() = tasks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemTaskBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTasks(tasks : List<Task>){
        this.tasks = tasks.sortedBy { task ->
            task.isComplete }
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.apply {
                checkBox.isChecked = task.isComplete
                toggleStar.isChecked = task.isStarred
                if(task.isComplete){
                    textViewTitle.paintFlags = textViewTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    textViewDetails.paintFlags = textViewDetails.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }else{
                    textViewTitle.paintFlags = textViewTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    textViewDetails.paintFlags = textViewDetails.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
                textViewTitle.text = task.title
                if(task.description.isNullOrEmpty()){
                    textViewDetails.visibility = View.GONE
                }else{
                    textViewDetails.text = task.description
                    textViewDetails.visibility = View.VISIBLE
                }


//                checkBox.setOnClickListener {
//                    val updatedTask = task.copy(isComplete = checkBox.isChecked)
//                    listener.onTaskUpdated(updatedTask)
//                }
//                toggleStar.setOnClickListener{
//                    val updatedTask = task.copy(isStarred = toggleStar.isChecked)
//                    listener.onTaskUpdated(updatedTask)
//                }
            checkBox.addOnCheckedStateChangedListener { _, state ->
                val updatedTask = when (state) {
                    MaterialCheckBox.STATE_CHECKED -> { task.copy(isComplete = true) }
                    else -> { task.copy(isComplete = false) }
                }

                listener.onTaskUpdated(updatedTask)
            }
            toggleStar.addOnCheckedStateChangedListener { _, state ->
                val updatedTask = when (state) {
                    MaterialCheckBox.STATE_CHECKED -> { task.copy(isStarred = true) }
                    else -> { task.copy(isStarred = false) }
                }
                listener.onTaskUpdated(updatedTask)
            }
            }
        }
    }

    interface TaskUpdatedListener {
        fun onTaskUpdated(task: Task)
    }

}

