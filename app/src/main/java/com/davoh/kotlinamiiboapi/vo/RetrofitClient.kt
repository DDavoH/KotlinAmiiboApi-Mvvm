package com.davoh.kotlinamiiboapi.vo

import com.davoh.kotlinamiiboapi.domain.WebService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val webservice: WebService by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.amiiboapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }

}