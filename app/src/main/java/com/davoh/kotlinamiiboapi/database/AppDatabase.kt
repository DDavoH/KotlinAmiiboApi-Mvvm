package com.davoh.kotlinamiiboapi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davoh.kotlinamiiboapi.database.model.AmiiboEntity

@Database(entities = [AmiiboEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun amiiboDao() : AmiiboDao
}