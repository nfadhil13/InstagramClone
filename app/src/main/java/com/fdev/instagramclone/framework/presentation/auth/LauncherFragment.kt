package com.fdev.instagramclone.framework.presentation.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentLauncherBinding
import com.fdev.instagramclone.databinding.FragmentSplashBinding


class LauncherFragment : Fragment() {

    private  var _binding : FragmentLauncherBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLauncherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
    }


    private fun initClickListener(){

        binding.apply {
            this.btnLogin.setOnClickListener {
                navToLogin()
            }

            this.btnSignup.setOnClickListener {
                navToSignup()
            }
        }

    }

    private fun navToLogin(){
        findNavController().navigate(R.id.action_launcherFragment_to_loginFragment)
    }

    private fun navToSignup(){
        findNavController().navigate(R.id.action_launcherFragment_to_signupFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}