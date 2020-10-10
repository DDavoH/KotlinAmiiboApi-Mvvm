package com.davoh.kotlinamiiboapi.domain

import com.davoh.kotlinamiiboapi.database.model.AmiiboList
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("amiibo")
    suspend fun getAmiiboByName(@Query("name") amiiboName:String): AmiiboList

    @GET("amiibo")
    suspend fun getAllAmiibos() : AmiiboList

}