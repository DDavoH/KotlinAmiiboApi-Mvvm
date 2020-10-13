package com.davoh.kotlinamiiboapi.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.davoh.kotlinamiiboapi.database.AmiiboDao
import com.davoh.kotlinamiiboapi.database.model.Amiibo
import com.davoh.kotlinamiiboapi.database.model.AmiiboEntity
import com.davoh.kotlinamiiboapi.database.model.asFavoriteAmiiboList
import com.davoh.kotlinamiiboapi.vo.Resource
import com.davoh.kotlinamiiboapi.vo.RetrofitClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class DataSourceImpl @Inject constructor(private val amiiboDao: AmiiboDao):DataSource{

    override suspend fun getAllAmiibos(): Resource<List<Amiibo>> {
        return Resource.Success(RetrofitClient.webservice.getAllAmiibos().amiiboList)
    }

    override suspend fun getAmiiboByName(amiiboName: String): Resource<List<Amiibo>> {
        return Resource.Success(RetrofitClient.webservice.getAmiiboByName(amiiboName).amiiboList)
    }

    override suspend fun insertAmiiboIntoRoom(amiibo: AmiiboEntity) {
        amiiboDao.insertFavoriteAmiibo(amiibo)
    }

    override fun getFavoritesAmiibos(): LiveData<List<Amiibo>>{
        return amiiboDao.getAllFavoriteAmiibos().map { it.asFavoriteAmiiboList() }
    }

    override suspend fun deleteAmiibo(amiibo: AmiiboEntity) {
        amiiboDao.deleteFavoriteAmiibo(amiibo)
    }

}