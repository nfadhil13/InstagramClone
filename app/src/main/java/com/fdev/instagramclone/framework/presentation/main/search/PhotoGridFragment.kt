package com.fdev.instagramclone.framework.presentation.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fdev.instagramclone.databinding.FragmentPhotoGridBinding
import com.fdev.instagramclone.databinding.FragmentProfileBinding
import com.fdev.instagramclone.framework.presentation.OnFragmentChangedListener
import com.fdev.instagramclone.framework.presentation.main.BaseMainFragment
import com.fdev.instagramclone.framework.presentation.main.MainActivity
import java.lang.ClassCastException

class PhotoGridFragment  : BaseMainFragment(){

    private var _binding : FragmentPhotoGridBinding? = null

    private val binding
        get() = _binding!!



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPhotoGridBinding.inflate(inflater , container , false)
        setOnFragmentChangedListener()
        onFragmentChangedListener.fragmentChanged(this)
        return binding.root
    }




}