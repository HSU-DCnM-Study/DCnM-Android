package com.example.todo_list

import retrofit2.Call
import retrofit2.http.*


interface RetrofitService {

    @GET("to-do/search/")
    fun searchToDoList(
        @HeaderMap headers: Map<String, String>,
        @Query("keyword") keyword: String
    ): Call<ArrayList<Todo>>

    @PUT("to-do/complete/{todoId}")
    fun changeToDoComplete(
        @HeaderMap headers: Map<String, String>,
        @Path("todoId") todoId: Int
    ): Call<Any>


    @GET("to-do/")
    fun getToDoList(
        @HeaderMap headers: Map<String, String>,
    ): Call<ArrayList<Todo>>

    @POST("to-do/")
    @FormUrlEncoded
    fun makeToDo(
        @HeaderMap headers: Map<String, String>,
        @FieldMap params: HashMap<String, Any>
    ): Call<Any>

}