package com.davoh.kotlinamiiboapi.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.davoh.kotlinamiiboapi.database.model.Amiibo
import com.davoh.kotlinamiiboapi.database.model.AmiiboEntity
import com.davoh.kotlinamiiboapi.database.model.asFavoriteAmiiboList
import com.davoh.kotlinamiiboapi.vo.Resource
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val dataSource: DataSource): Repository{
    override suspend fun getAllAmiibosList(): Resource<List<Amiibo>> {
        return dataSource.getAllAmiibos()
    }

    override suspend fun getAmiibosList(amiiboName: String): Resource<List<Amiibo>> {
        return dataSource.getAmiiboByName(amiiboName)
    }

    override fun getFavoritesAmiibos(): LiveData<List<Amiibo>> {
        return dataSource.getFavoritesAmiibos()
    }

    override suspend fun insertAmiibo(amiibo: AmiiboEntity) {
        dataSource.insertAmiiboIntoRoom(amiibo)
    }

    override suspend fun deleteAmiibo(amiibo: AmiiboEntity) {
        dataSource.deleteAmiibo(amiibo)
    }

    override suspend fun isAmiiboFavorite(amiibo: Amiibo): Boolean =
        dataSource.isAmiiboFavorite(amiibo)


}