package com.fdev.instagramclone.framework.presentation.main.account.state

import android.os.Parcelable
import androidx.hilt.Assisted
import androidx.lifecycle.SavedStateHandle
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.util.SessionManager
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileViewState (

       var userProfileViewState : UserProfileViewState? = null

) : Parcelable

@Parcelize
data class UserProfileViewState(
        var user : User
) : Parcelable