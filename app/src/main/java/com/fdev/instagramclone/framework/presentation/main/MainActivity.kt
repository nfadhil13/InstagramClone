package com.fdev.instagramclone.framework.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.ActivityAuthBinding
import com.fdev.instagramclone.databinding.ActivityMainBinding
import com.fdev.instagramclone.util.SessionManager
import com.fdev.instagramclone.util.printLogD
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        })



        setUpNavigation()
        sessionManager.currentUser.observe(this, Observer {
        })

    }

    private fun setUpNavigation() {
        binding.bottomNav.itemIconTintList = null

        val navController = findNavController(R.id.main_nav_host_fragment_container)
        binding.bottomNav.setupWithNavController(navController)

    }



}