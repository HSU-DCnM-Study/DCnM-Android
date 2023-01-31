package com.example.todo_list


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.databinding.ActivityTodoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTodoBinding
    private lateinit var todoRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.write.setOnClickListener {
            startActivity(Intent(this, TodoWriteActivity::class.java))
        }
        todoRecyclerView = findViewById(R.id.todo_list)
        getToDoList()
        findViewById<EditText>(R.id.search_edittext).doAfterTextChanged {
            searchDoToList(it.toString())
        }

    }

    private fun searchDoToList(keyword: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val todoService = retrofit.create(TodoService::class.java)

        val header = HashMap<String, String>()
        val sp = this.getSharedPreferences(
            "user_info",
            Context.MODE_PRIVATE
        )
        val token = sp.getString("token", "")
        header.put("Authorization", "token " + token!!)
        todoService.searchToDoList(header, keyword)
            .enqueue(object : Callback<ArrayList<Todo>> {
                override fun onResponse(
                    call: Call<ArrayList<Todo>>,
                    response: Response<ArrayList<Todo>>
                ) {
                    if (response.isSuccessful) {
                        val todoList = response.body()
                        makeToDoList(todoList!!)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Todo>>, t: Throwable) {
                }
            })

    }

    fun makeToDoList(todoList: ArrayList<Todo>) {
        todoRecyclerView.adapter = TodoListAdapter(
            todoList,
            LayoutInflater.from(this@TodoActivity),
            this@TodoActivity
        )
    }


    fun changeToDoComplete(todoId: Int, activity: TodoActivity) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val todoService = retrofit.create(TodoService::class.java)

        val header = HashMap<String, String>()
        val sp = this.getSharedPreferences(
            "user_info",
            Context.MODE_PRIVATE
        )
        val token = sp.getString("token", "")
        header.put("Authorization", "token " + token!!)

        todoService.changeToDoComplete(header, todoId).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                activity.getToDoList()
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                activity.getToDoList()
            }
        })
    }


    fun getToDoList() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val todoService = retrofit.create(TodoService::class.java)

        val header = HashMap<String, String>()
        val sp = this.getSharedPreferences(
            "user_info",
            Context.MODE_PRIVATE
        )
        Log.d("preferences", sp.toString())
        val token = sp.getString("token", "")
        header["Authorization"] = "token " + token!!

        todoService.getToDoList(header).enqueue(object : Callback<ArrayList<Todo>> {
            override fun onResponse(
                call: Call<ArrayList<Todo>>,
                response: Response<ArrayList<Todo>>
            ) {
                if (response.isSuccessful) {
                    val todoList = response.body()
                    Log.d("Retrofit", todoList.toString())
                    makeToDoList(todoList!!)
                }
            }

            override fun onFailure(call: Call<ArrayList<Todo>>, t: Throwable) {
                Log.d("Retrofit", t.toString())
            }
        })

    }
}