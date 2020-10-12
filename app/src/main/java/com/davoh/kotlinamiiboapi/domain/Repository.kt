package com.davoh.kotlinamiiboapi.domain

import com.davoh.kotlinamiiboapi.database.model.Amiibo
import com.davoh.kotlinamiiboapi.database.model.AmiiboEntity
import com.davoh.kotlinamiiboapi.vo.Resource

interface Repository {
    suspend fun getAllAmiibosList(): Resource<List<Amiibo>>

    suspend fun getAmiibosList(amiiboName:String): Resource<List<Amiibo>>

    suspend fun getFavoritesAmiibos(): Resource<List<AmiiboEntity>>
    suspend fun insertAmiibo(amiibo: AmiiboEntity)
    suspend fun deleteAmiibo(amiibo:AmiiboEntity)

}