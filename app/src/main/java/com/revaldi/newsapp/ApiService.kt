package com.revaldi.newsapp
import com.revaldi.newsapp.Data.NewsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface NewsApiService {
    @GET("NewsAPI/top-headlines/category/health/in.json")
    suspend fun getHealthHeadlines(): NewsResponse
}

private val retrofit = Retrofit.Builder()
    .baseUrl("https://saurav.tech/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val newsApiService: NewsApiService = retrofit.create(NewsApiService::class.java)
