package com.fdev.instagramclone.framework.presentation.auth.state

import com.fdev.instagramclone.business.domain.state.StateEvent

sealed class AuthStateEvent : StateEvent {

    class LoginStateEvent(
            var email : String,
            var password : String
    ) : AuthStateEvent(){
        override fun errorInfo(): String {
            return "Error Logging in"
        }

        override fun eventName(): String {
            return "AuthStateEvent"
        }

        override fun shouldDisplayProgressBar() = true

    }

}