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

    @Query("SELECT * FROM amiibos_entity WHERE amiibo_head = :amiibo_head AND amiibo_tail = :amiibo_tail")
    suspend fun getAmiiboById(amiibo_head: String, amiibo_tail: String): AmiiboEntity?



}