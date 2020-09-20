package com.fdev.instagramclone.framework.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentHomeBinding
import com.fdev.instagramclone.framework.presentation.OnFragmentChangedListener
import com.fdev.instagramclone.framework.presentation.UIController
import com.fdev.instagramclone.framework.presentation.auth.AuthActivity
import com.fdev.instagramclone.framework.presentation.main.BaseMainFragment
import com.fdev.instagramclone.framework.presentation.main.MainActivity
import com.fdev.instagramclone.util.TodoCallback
import com.fdev.instagramclone.util.printLogD
import java.lang.ClassCastException

class HomeFragment  : BaseMainFragment() {

    private var _binding : FragmentHomeBinding? = null

    private val binding
        get() = _binding!!




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }









}