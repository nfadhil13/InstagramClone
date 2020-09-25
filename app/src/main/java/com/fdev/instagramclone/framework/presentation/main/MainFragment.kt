package com.fdev.instagramclone.framework.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentMainBinding
import com.fdev.instagramclone.framework.presentation.main.chat.ChatListFragment
import com.fdev.instagramclone.framework.presentation.main.home.HomeFragment
import com.fdev.instagramclone.util.printLogD

class MainFragment : Fragment(){

    private var _binding : FragmentMainBinding? = null

    private val binding
        get() = _binding!!



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater , container , false)
        printLogD("MainFragment" , "onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBottomNavigation()



    }


    private fun setUpBottomNavigation() {
        binding.bottomNav.itemIconTintList = null
        val bottomNavFragmentContainer = childFragmentManager.findFragmentById(R.id.bottomnav_nav_host_containerr) as? NavHostFragment
        val navController = bottomNavFragmentContainer?.navController
        navController?.let {
            binding.bottomNav.setupWithNavController(navController)
            printLogD("MainFragment" , "Setting up MainFragment bottom navigation")
        }?: throw Exception("Failed to get navigation controller of childFragment")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }





}