package com.devkanhaiya.newsappwithretrofit.API

data class NewsApiJson(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)