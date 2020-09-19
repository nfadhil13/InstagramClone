package com.fdev.instagramclone.framework.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentHomeBinding
import com.fdev.instagramclone.framework.presentation.main.MainFragment
import com.fdev.instagramclone.util.TodoCallback

class HomeFragment  : Fragment() {

    private var _binding : FragmentHomeBinding? = null

    private val binding
        get() = _binding!!



    lateinit var todoCallback: TodoCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCallBack()
        binding.test.setOnClickListener {
            todoCallback.execute()
        }
    }

    private fun initCallBack() {
        todoCallback = (pa
    }

    private fun callBack(){
        todoCallback.execute()
    }





}