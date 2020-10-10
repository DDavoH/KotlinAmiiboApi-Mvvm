package com.davoh.kotlinamiiboapi.di

import android.content.Context
import androidx.room.Room
import com.davoh.kotlinamiiboapi.database.AmiiboDao
import com.davoh.kotlinamiiboapi.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule{

    @Singleton
    @Provides
    fun RoomInstance(@ApplicationContext context: Context)=
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "amiibos_tabla").build()


    @Singleton
    @Provides
    fun AmiiboDao(db: AppDatabase) = db.amiiboDao()

}