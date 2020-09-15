package com.fdev.instagramclone.framework.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentForgetPasswordBinding
import com.fdev.instagramclone.databinding.FragmentLoginBinding

class ForgetPasswordFragment : Fragment(){

    private var _binding: FragmentForgetPasswordBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentForgetPasswordBinding.inflate(inflater,container,false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
    }

    private fun initClickListener() {
        binding.apply{
            this.btnForgetPasswordNext.setOnClickListener {
                // Send verfication code to email

                navToNewPassword()
            }
        }
    }

    private fun navToNewPassword(){
        findNavController().navigate(R.id.action_forgetPasswordFragment_to_confirmationCodeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}