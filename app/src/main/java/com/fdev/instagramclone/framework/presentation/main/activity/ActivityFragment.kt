package com.fdev.instagramclone.framework.presentation.main.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fdev.instagramclone.databinding.FragmentActivityBinding
import com.fdev.instagramclone.databinding.FragmentAddPhotoBinding

class ActivityFragment : Fragment(){

    private var _binding : FragmentActivityBinding? = null

    private val binding
        get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentActivityBinding.inflate(inflater , container , false)
        return binding.root
    }

}