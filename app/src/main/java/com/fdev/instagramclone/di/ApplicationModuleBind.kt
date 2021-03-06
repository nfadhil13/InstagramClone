package com.fdev.instagramclone.di

import com.fdev.instagramclone.business.data.network.abstraction.UserNetworkDataSource
import com.fdev.instagramclone.business.data.network.implementation.UserNetworkDataSourceImpl
import com.fdev.instagramclone.framework.datasource.network.abstraction.UserFirestoreService
import com.fdev.instagramclone.framework.datasource.network.implementation.UserFirestoreServiceImpl
import com.fdev.instagramclone.framework.datasource.network.mapper.UserNetworkMapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class ApplicationModuleBind{


    @Binds
    abstract fun bindUserFirestoreService(userFirestoreServiceImpl: UserFirestoreServiceImpl) : UserFirestoreService

    @Binds
    abstract fun bindUserNetworkDataSource(userNetworkDataSourceImpl: UserNetworkDataSourceImpl) : UserNetworkDataSource


}