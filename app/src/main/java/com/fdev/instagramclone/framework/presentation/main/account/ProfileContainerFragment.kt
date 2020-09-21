package com.fdev.instagramclone.framework.presentation.main.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fdev.instagramclone.databinding.FragmentProfileContainerBinding
import com.fdev.instagramclone.framework.presentation.main.BaseMainFragment

class ProfileContainerFragment : BaseMainFragment(){

    private var _binding : FragmentProfileContainerBinding? = null

    private val binding
        get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileContainerBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}