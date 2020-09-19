package com.fdev.instagramclone.framework.presentation.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.fdev.instagramclone.R
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.databinding.ActivityAuthBinding
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


    private  var _binding : ActivityAuthBinding? = null

<<<<<<< Updated upstream
=======
    @Inject
    lateinit var sessionManager : SessionManager


    private val user = User(
    )

>>>>>>> Stashed changes
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
<<<<<<< Updated upstream

=======
        subsribeObserver()
        sessionManager.login(user)


    }


    private fun subsribeObserver() {

        sessionManager.currentUser.observe(this , androidx.lifecycle.Observer {
            it?.let{
                goToMainActivity()
            }
        })
    }

    private fun goToMainActivity() {
        val intent = Intent(this , MainActivity::class.java)
        startActivity(intent)
        finish()
>>>>>>> Stashed changes
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




}