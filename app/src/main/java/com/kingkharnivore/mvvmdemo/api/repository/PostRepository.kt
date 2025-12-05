package com.kingkharnivore.mvvmdemo.api.repository

import com.kingkharnivore.mvvmdemo.api.PostApi
import com.kingkharnivore.mvvmdemo.model.Post
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostRepository {

    private val api: PostApi = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PostApi::class.java)

    suspend fun getPosts(): Result<List<Post>> {
        return try {
            Result.success(api.getPosts())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}