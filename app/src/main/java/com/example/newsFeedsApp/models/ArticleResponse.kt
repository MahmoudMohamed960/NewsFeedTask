package com.example.newsFeedsApp.models

data class ArticleResponse(
    val articles: MutableList<Article>?,
    val sortBy: String?,
    val source: String?,
    val status: String?
)