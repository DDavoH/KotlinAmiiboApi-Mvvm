package com.davoh.kotlinamiiboapi.domain

import androidx.lifecycle.LiveData
import com.davoh.kotlinamiiboapi.database.model.Amiibo
import com.davoh.kotlinamiiboapi.database.model.AmiiboEntity
import com.davoh.kotlinamiiboapi.vo.Resource

interface Repository {
    suspend fun getAllAmiibosList(): Resource<List<Amiibo>>

    suspend fun getAmiibosList(amiiboName:String): Resource<List<Amiibo>>

    fun getFavoritesAmiibos(): LiveData<List<Amiibo>>
    suspend fun insertAmiibo(amiibo: AmiiboEntity)
    suspend fun deleteAmiibo(amiibo:AmiiboEntity)

}