package com.fdev.instagramclone.framework.presentation.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.fdev.instagramclone.R
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.business.domain.state.Response
import com.fdev.instagramclone.business.domain.state.UIComponentType
import com.fdev.instagramclone.databinding.ActivityAuthBinding
import com.fdev.instagramclone.framework.datasource.network.implementation.UserFirestoreServiceImpl
import com.fdev.instagramclone.framework.datasource.network.mapper.UserNetworkMapper
import com.fdev.instagramclone.framework.presentation.auth.state.AuthStateEvent
import com.fdev.instagramclone.util.cLog
import com.fdev.instagramclone.util.printLogD
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {


    private  var _binding : ActivityAuthBinding? = null


    private val viewModel : AuthViewModel by viewModels()

    private val binding
        get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        subsribeObserver()

    }


    private fun subsribeObserver() {
        viewModel.shouldDisplayProgressBar.observe(this, androidx.lifecycle.Observer {
            printLogD("AuthActivity" , "New Message progressbar status ${it}")
            binding.mainProgressbar.show(it)
        })

        viewModel.stateMessage.observe(this , androidx.lifecycle.Observer {
            it?.let{

                viewModel.clearAllStateMessages()
            }

        })
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun ProgressBar.show(show : Boolean){
        if(show){
            visibility = View.VISIBLE
        }else{
            visibility = View.INVISIBLE
        }
    }




}