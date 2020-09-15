package com.fdev.instagramclone.framework.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val binding
        get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
    }

    private fun initClickListener() {
        binding.apply {
            goToSignupBtn.setOnClickListener {
                navToSignup()
            }

            forgetPasswordBtn.setOnClickListener {
                navToForgetPassword()
            }

            btnLogin.setOnClickListener {
                // Do something to log in the user
            }
        }
    }

    private fun navToSignup() {
        findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
    }

    private fun navToForgetPassword() {
        findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}