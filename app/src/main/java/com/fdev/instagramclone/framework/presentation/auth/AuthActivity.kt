package com.fdev.instagramclone.framework.presentation.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fdev.instagramclone.R
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.framework.datasource.network.implementation.UserFirestoreServiceImpl
import com.fdev.instagramclone.framework.datasource.network.mapper.UserNetworkMapper
import com.fdev.instagramclone.util.cLog
import com.fdev.instagramclone.util.printLogD
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {


    @Inject
    lateinit var firestoreServiceImpl: UserFirestoreServiceImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }


}