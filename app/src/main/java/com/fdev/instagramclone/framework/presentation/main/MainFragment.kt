package com.fdev.instagramclone.framework.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentMainBinding
import com.fdev.instagramclone.framework.presentation.main.home.HomeFragment
import com.fdev.instagramclone.util.TodoCallback
import com.fdev.instagramclone.util.printLogD

class MainFragment : Fragment() , TodoCallback{

    private var _binding : FragmentMainBinding? = null

    private val binding
        get() = _binding!!



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNavigation()

    }


    private fun setUpNavigation() {
        binding.bottomNav.itemIconTintList = null
        val navController = requireActivity().findNavController(R.id.nav_main_host_container)
        binding.bottomNav.setupWithNavController(navController)
    }



    override fun execute() {
        findNavController().navigate(R.id.action_mainFragment_to_addPhotoFragment2)
    }
}