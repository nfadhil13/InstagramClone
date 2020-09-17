package com.fdev.instagramclone.framework.presentation.auth.state

import android.os.Parcelable
import com.fdev.instagramclone.business.domain.model.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthViewState(

        var waitVerifiedViewState: WaitVerifiedViewState? = null,

        var forgetPasswordViewState: ForgetPasswordViewState? = null,

        var inputNamePasswordViewState: EnterNamePasswordViewState? = null,

        var loginViewState: LoginViewState? = null,

        var newPasswordViewState: NewPasswordViewState? = null,

        var signUpViewState: SignUpViewState? = null,


        ) : Parcelable


@Parcelize
data class WaitVerifiedViewState(
        var userVerfiedStatus : Boolean = false,
        var verifiedUser : User? = null,
        var tempPassword: String? = null
) : Parcelable {

}

@Parcelize
data class ForgetPasswordViewState(
        var forgotenAccountEmail : String? = null,
        var isSuccess : Boolean = false
) : Parcelable


@Parcelize
data class EnterNamePasswordViewState(
        val user : User? = null
) : Parcelable

@Parcelize
data class LoginViewState(
        var email : String?  = null,
        var password : String? = null,
        var succesUser : User? = null
) : Parcelable

@Parcelize
data class NewPasswordViewState(
        var newpassword : String?  = null
) : Parcelable

@Parcelize
data class SignUpViewState(
        var email : String? = null,
        var mode : String? = null,
        var number : String? = null,
        var succesUser : User? = null,
        var tempPassword : String? = null
) : Parcelable {
    companion object{
        const val SIGPUP_WITH_EMAIL = "EMAIL"
        const val SIGNUP_WITH_NUMBER = "NUMBER"
    }
}
