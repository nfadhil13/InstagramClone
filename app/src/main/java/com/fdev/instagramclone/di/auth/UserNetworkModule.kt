package com.fdev.instagramclone.di.auth

import com.fdev.instagramclone.framework.datasource.network.mapper.UserNetworkMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
object UserNetworkModule {

    @Provides
    fun provideUserNetworkMapper() : UserNetworkMapper {
        return UserNetworkMapper()
    }

}