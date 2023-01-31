package com.example.todo_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoListAdapter(
    val todoList: ArrayList<Todo>,
    private val inflater: LayoutInflater,
    val activity: TodoActivity
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var previousDate: String = ""

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView

        init {
            dateTextView = itemView.findViewById(R.id.date)
        }

    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val content: TextView
        val isComplete: ImageView

        init {
            content = itemView.findViewById(R.id.content)
            isComplete = itemView.findViewById(R.id.is_complete)
            isComplete.setOnClickListener {
                activity.changeToDoComplete(todoList[adapterPosition].id, activity)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        val todo = todoList.get(position)
        val tempDate = todo.created.split("T")[0]
        return if (previousDate == tempDate) {
            0
        } else {
            previousDate = tempDate
            1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> DateViewHolder(inflater.inflate(R.layout.todo_date, parent, false))
            else -> ContentViewHolder(inflater.inflate(R.layout.todo_content, parent, false))
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val todo = todoList[position]
        if (holder is DateViewHolder) {
            holder.dateTextView.text = todo.created.split("T")[0]
        } else {
            (holder as ContentViewHolder).content.text = todo.content
            if (todo.is_complete) {
                holder.isComplete.setImageDrawable(
                    activity.resources.getDrawable(
                        R.drawable.btn_radio_check,
                        activity.theme
                    )
                )
            } else {
                holder.isComplete.setImageDrawable(
                    activity.resources.getDrawable(
                        R.drawable.btn_radio,
                        activity.theme
                    )
                )
            }
        }

    }

    override fun getItemCount(): Int {
        return todoList.size
    }
}