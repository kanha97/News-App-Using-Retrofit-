package com.devkanhaiya.newsappwithretrofit

import com.devkanhaiya.newsappwithretrofit.API.NewsApiJson
import retrofit2.http.GET

interface Apirequest {
    @GET("/v2/top-headlines?country=in&category=technology&apiKey=a76d1362ad094e63b2c27cb8f0312a1d")
    suspend fun getNews() :NewsApiJson
}