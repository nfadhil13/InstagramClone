package com.fdev.instagramclone.framework.presentation.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fdev.instagramclone.databinding.FragmentPhotoGridBinding
import com.fdev.instagramclone.databinding.FragmentProfileBinding

class PhotoGridFragment  : Fragment(){

    private var _binding : FragmentPhotoGridBinding? = null

    private val binding
        get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPhotoGridBinding.inflate(inflater , container , false)
        return binding.root
    }


}