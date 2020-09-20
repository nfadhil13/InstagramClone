package com.fdev.instagramclone.framework.presentation.main.addphoto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fdev.instagramclone.databinding.FragmentAddPhotoBinding
import com.fdev.instagramclone.databinding.FragmentPhotoGridBinding
import com.fdev.instagramclone.framework.presentation.main.BaseMainFragment

class AddPhotoFragment : BaseMainFragment(){

    private var _binding : FragmentAddPhotoBinding? = null

    private val binding
        get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddPhotoBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}