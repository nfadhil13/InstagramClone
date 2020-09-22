package com.fdev.instagramclone.framework.presentation.main.account

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.business.domain.state.StateEvent
import com.fdev.instagramclone.framework.presentation.BaseViewModel
import com.fdev.instagramclone.framework.presentation.main.account.state.ProfileViewState
import com.fdev.instagramclone.util.SessionManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class ProfileViewModel
@ViewModelInject
constructor(
        val sessionManager: SessionManager,
        @Assisted private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<ProfileViewState>() {

    override fun handleNewData(data: ProfileViewState) {

    }

    override fun setStateEvent(stateEvent: StateEvent) {

    }

    fun setCurrentUser(user : User){
        sessionManager.login(user)
    }

    fun getCurrentUser() = sessionManager.currentUser

    override fun initNewViewState(): ProfileViewState {
        return ProfileViewState()
    }

}