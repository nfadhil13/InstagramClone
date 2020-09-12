package com.fdev.instagramclone.framework.presentation.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fdev.instagramclone.R
import com.fdev.instagramclone.framework.datasource.network.implementation.UserFirestoreServiceImpl
import com.fdev.instagramclone.framework.datasource.network.mapper.UserNetworkMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val testUserLogIn = UserFirestoreServiceImpl(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance(), UserNetworkMapper())
        
    }


}