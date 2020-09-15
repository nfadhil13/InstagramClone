package com.fdev.instagramclone.framework.presentation.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentSignupBinding


class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null

    private val binding
        get() = _binding!!



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = FragmentSignupBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListner()
    }

    private fun initClickListner() {
        binding.apply {

            loginBtnTv.setOnClickListener{
                navToLogin()
            }

            btnSignupNext.setOnClickListener {
                //Do signup thing

                navToConfirmationCode()
            }

            loginMethodEmailTv.setOnClickListener {
                setTOEmailMode()
            }

            loginMethodPhoneTv.setOnClickListener {
                setToNumberMode()
            }
        }
    }

    private fun navToLogin() {
        findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
    }

    private fun navToConfirmationCode() {
        findNavController().navigate(R.id.action_signupFragment_to_confirmationCodeFragment)
    }

    private fun setTOEmailMode(){
        binding.apply {
           context?.let{
               loginMethodEmailLine.setBackgroundColor(ContextCompat.getColor(it,R.color.blackLine))
               loginMethodEmailTv.setTextColor(ContextCompat.getColor(it,R.color.blackLine))

               loginMethodPhoneLine.setBackgroundColor(ContextCompat.getColor(it,R.color.blurLine))
               loginMethodPhoneTv.setTextColor(ContextCompat.getColor(it,R.color.blurLine))

               emailEditText.setHint(resources.getString(R.string.edit_text_sign_up_email_hint))
               btnSignupNext.isEnabled = true
           }
        }
    }

    private fun setToNumberMode(){
        binding.apply {
            context?.let{
                loginMethodEmailLine.setBackgroundColor(ContextCompat.getColor(it,R.color.blurLine))
                loginMethodEmailTv.setTextColor(ContextCompat.getColor(it,R.color.blurLine))

                loginMethodPhoneLine.setBackgroundColor(ContextCompat.getColor(it,R.color.blackLine))
                loginMethodPhoneTv.setTextColor(ContextCompat.getColor(it,R.color.blackLine))

                emailEditText.setHint("THIS FEATURE IS NOT READY YET")
                btnSignupNext.isEnabled = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}