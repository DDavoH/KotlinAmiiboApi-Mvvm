package com.davoh.kotlinamiiboapi.domain

import com.davoh.kotlinamiiboapi.database.AmiiboDao
import com.davoh.kotlinamiiboapi.database.model.Amiibo
import com.davoh.kotlinamiiboapi.database.model.AmiiboEntity
import com.davoh.kotlinamiiboapi.vo.Resource
import com.davoh.kotlinamiiboapi.vo.RetrofitClient
import javax.inject.Inject

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

    override suspend fun getFavoritesAmiibos(): Resource<List<AmiiboEntity>> {
      return Resource.Success(amiiboDao.getAllFavoriteAmiibos())
    }

    override suspend fun deleteAmiibo(amiibo: AmiiboEntity) {
        amiiboDao.deleteFavoriteAmiibo(amiibo)
    }


}