package com.fdev.instagramclone.framework.presentation.main.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentHomeBinding
import com.fdev.instagramclone.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(){

    private var _binding : FragmentProfileBinding? = null

    private val binding
        get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater , container , false)
        return binding.root
    }


}