package com.fdev.instagramclone.framework.presentation.auth.state

import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.business.domain.state.StateEvent

sealed class AuthStateEvent : StateEvent {

    class LoginStateEvent(
            var email: String,
            var password: String
    ) : AuthStateEvent() {
        override fun errorInfo(): String {
            return "Error Logging in"
        }

        override fun eventName(): String {
            return "AuthStateEvent"
        }

        override fun shouldDisplayProgressBar() = true

    }

    class DeleteUserStateEvent(
            var user: User
    ) : AuthStateEvent() {
        override fun errorInfo(): String {
            return "Failed to delete user"
        }

        override fun eventName(): String {
            return "DeleteUserStateEvent"
        }

        override fun shouldDisplayProgressBar() = false

    }

    class SignupStateEvent(
            var email: String
    ) : AuthStateEvent() {
        override fun errorInfo(): String {
            return "Sign up failed"
        }

        override fun eventName(): String {
            return "SignupStateEvent"
        }

        override fun shouldDisplayProgressBar(): Boolean {
            return true
        }

    }

    class CheckUserVerifiedStateEvent(
            var password: String
    ) : AuthStateEvent() {
        override fun errorInfo(): String {
            return "Error get user status"
        }

        override fun eventName(): String {
            return "CheckUserVerifiedStateEvent"
        }

        override fun shouldDisplayProgressBar(): Boolean {
            return true
        }

    }

    class InputNamePassword(var user : User ,var  password: String) : AuthStateEvent(){
        override fun errorInfo(): String = "Error to input"

        override fun eventName(): String =  "InputNamePassword"

        override fun shouldDisplayProgressBar(): Boolean = true

    }

    class ForgetPassword(var email : String) : AuthStateEvent(){
        override fun errorInfo(): String = "Failed to sent email"

        override fun eventName(): String =  "ForgetPassword"

        override fun shouldDisplayProgressBar(): Boolean = true
    }

}