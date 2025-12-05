package com.kingkharnivore.mvvmdemo.api

import com.kingkharnivore.mvvmdemo.model.Post
import retrofit2.http.GET

interface PostApi {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}