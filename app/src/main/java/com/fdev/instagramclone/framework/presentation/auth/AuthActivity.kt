package com.fdev.instagramclone.framework.presentation.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.databinding.ActivityAuthBinding
import com.fdev.instagramclone.framework.presentation.main.MainActivity
import com.fdev.instagramclone.util.SessionManager
import dagger.hilt.android.AndroidEntryPoint

import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {


    private  var _binding : ActivityAuthBinding? = null

    @Inject
    lateinit var sessionManager : SessionManager


    private val user = User()

    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




}