package com.example.newsFeedsApp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {
    @GET("articles")
    suspend fun getArticlesList(@Query("source") articleSource:String,@Query("apiKey") apiKey:String):Response<ArticleResponse>
}