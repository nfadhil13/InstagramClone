package com.fdev.instagramclone.di.auth

import com.fdev.instagramclone.business.data.network.abstraction.UserNetworkDataSource
import com.fdev.instagramclone.business.interactors.auth.AuthInteractors
import com.fdev.instagramclone.business.interactors.auth.LogIn
import com.fdev.instagramclone.framework.datasource.network.abstraction.UserFirestoreService
import com.fdev.instagramclone.framework.datasource.network.mapper.UserNetworkMapper
import com.fdev.instagramclone.framework.presentation.auth.AuthViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
object UserNetworkModule {





}