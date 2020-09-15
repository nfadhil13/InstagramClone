package com.fdev.instagramclone.framework.presentation.auth

import androidx.hilt.lifecycle.ViewModelInject
import com.fdev.instagramclone.business.domain.state.StateEvent
import com.fdev.instagramclone.business.interactors.auth.AuthInteractors
import com.fdev.instagramclone.framework.presentation.BaseViewModel
import com.fdev.instagramclone.framework.presentation.auth.state.AuthStateEvent
import com.fdev.instagramclone.framework.presentation.auth.state.AuthViewState
import com.fdev.instagramclone.util.printLogD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class AuthViewModel
@ViewModelInject
    constructor(
            private val authInteractor : AuthInteractors
    ): BaseViewModel<AuthViewState>(){

    override fun handleNewData(data: AuthViewState) {
       data.let{ viewState ->
           viewState.succesUser?.let {user->
                printLogD("AuthViewModel" , "Success Log in : Logging in .. with ${user.username}")
           }
       }
    }

    override fun setStateEvent(stateEvent: StateEvent) {

        when(stateEvent){

            is AuthStateEvent.LoginStateEvent -> {
                authInteractor.loginInteractor.logInWithEmail(stateEvent.email , stateEvent.password , stateEvent)
            }

        }
    }

    override fun initNewViewState(): AuthViewState {
        return AuthViewState()
    }

}