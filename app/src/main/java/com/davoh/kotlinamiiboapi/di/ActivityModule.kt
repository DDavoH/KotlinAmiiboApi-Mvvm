package com.davoh.kotlinamiiboapi.di

import com.davoh.kotlinamiiboapi.domain.DataSource
import com.davoh.kotlinamiiboapi.domain.DataSourceImpl
import com.davoh.kotlinamiiboapi.domain.Repository
import com.davoh.kotlinamiiboapi.domain.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityModule {

    @Binds
    abstract fun bindRepositoryImpl(repositoryImpl: RepositoryImpl) : Repository

    @Binds
    abstract fun bindDataSourceImpl(dataSourceImpl: DataSourceImpl): DataSource

}