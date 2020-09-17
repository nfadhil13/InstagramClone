package com.fdev.instagramclone.framework.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.ActivityAuthBinding
import com.fdev.instagramclone.databinding.ActivityMainBinding
import com.fdev.instagramclone.util.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null

    private val binding
        get() = _binding!!

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sessionManager.currentUser.observe(this, Observer {
            binding.testUser.setText("Welcome ${it?.username}")
        })

    }
}