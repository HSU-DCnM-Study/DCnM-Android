package com.example.todo_list

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.todo_list.databinding.ActivityTodoWriteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TodoWriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodoWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)

        binding.makeTodo.setOnClickListener {
            val body = hashMapOf<String, Any>()
            body["content"] = binding.contentEditText.text
            body["is_complete"] = "False"

            val header = HashMap<String, String>()
            val sp = this.getSharedPreferences(
                "user_info",
                Context.MODE_PRIVATE
            )
            val token = sp.getString("token", "")
            header["Authorization"] = "token " + token!!

            retrofitService.makeToDo(header, body).enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    onBackPressed()
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    onBackPressed()
                }
            })
        }
    }
}