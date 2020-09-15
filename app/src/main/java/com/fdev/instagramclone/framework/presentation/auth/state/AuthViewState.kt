package com.fdev.instagramclone.framework.presentation.auth.state

import android.os.Parcelable
import com.fdev.instagramclone.business.domain.model.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthViewState (

        var confirmationCodeViewState: ConfirmationCodeViewState? = null ,

        var forgetPasswordViewState: ForgetPasswordViewState? = null,

        var inputNamePasswordViewState: EnterNamePasswordViewState? = null,

        var loginViewState: LoginViewState? = null,

        var newPasswordViewState: NewPasswordViewState? = null ,

        var signUpViewState: SignUpViewState? = null,

        var succesUser : User?  = null

) : Parcelable


@Parcelize
data class ConfirmationCodeViewState(
        var enteredCode : String? = null
) : Parcelable

@Parcelize
data class ForgetPasswordViewState(
        var forgotenAccountEmail : String? = null
) : Parcelable


@Parcelize
data class EnterNamePasswordViewState(
        var username : String? = null,
        var fullName : String? = null,
        var password : String? = null
) : Parcelable

@Parcelize
data class LoginViewState(
        var email : String?  = null,
        var password : String? = null
) : Parcelable

@Parcelize
data class NewPasswordViewState(
        var newpassword : String?  = null
) : Parcelable

@Parcelize
data class SignUpViewState(
        var email : String?,
        var mode : String,
        var number : String?
) : Parcelable {
    companion object{
        const val SIGPUP_WITH_EMAIL = "EMAIL"
        const val SIGNUP_WITH_NUMBER = "NUMBER"
    }
}
