package com.fdev.instagramclone.framework.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentConfirmationCodeBinding

class ConfirmationCodeFragment : Fragment() {

    private var _binding: FragmentConfirmationCodeBinding? = null

    private val binding
        get() = _binding!!


    private var comingFrom = 1
    companion object{
        const val FROM_FORGET_PASSWORD = 1
        const val FROM_NEWACCOUNT = 2
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentConfirmationCodeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOnClickListener()
    }

    private fun initOnClickListener() {
        binding.apply {
            this.btnConfirmationNext.setOnClickListener {
                if(comingFrom == FROM_FORGET_PASSWORD){
                    //Do something

                    navToNewPassword()
                }else/* FROM NEW ACCOUNT */{

                    //Do somotehing

                    navToInputNamePassword()
                }
            }
        }
    }

    private fun navToInputNamePassword() {
        findNavController().navigate(R.id.action_confirmationCodeFragment_to_newPasswordFragment)
    }

    private fun navToNewPassword() {
        findNavController().navigate(R.id.action_confirmationCodeFragment_to_inputNamePasswordFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}