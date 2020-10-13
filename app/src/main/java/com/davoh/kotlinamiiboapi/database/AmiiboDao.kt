package com.davoh.kotlinamiiboapi.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.davoh.kotlinamiiboapi.database.model.AmiiboEntity

@Dao
interface AmiiboDao {
    @Query("SELECT * FROM amiibos_entity")
    fun getAllFavoriteAmiibos(): LiveData<List<AmiiboEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteAmiibo(amiibo: AmiiboEntity)

    @Delete
    suspend fun deleteFavoriteAmiibo(amiibo: AmiiboEntity)
}