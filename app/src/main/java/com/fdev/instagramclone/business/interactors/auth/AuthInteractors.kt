package com.fdev.instagramclone.business.interactors.auth

import javax.inject.Inject

class AuthInteractors @Inject constructor(
        val loginInteractor : LogIn,
        val signUpInteractor : SignUp,
        val checkIsUserVerifiedInteractor: CheckIsUserVerified,
        val inputNamePassword: InputNamePassword,
        val resetPassword: ResetPassword,
        val resendVerification: ResendVerification,
        val syncAndGetLastUser: SyncAndGetLastUser
)