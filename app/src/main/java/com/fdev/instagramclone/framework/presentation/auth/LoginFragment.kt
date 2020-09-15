package com.fdev.instagramclone.framework.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentLoginBinding
import com.fdev.instagramclone.framework.presentation.auth.state.AuthStateEvent
import com.fdev.instagramclone.util.printLogD

class LoginFragment : Fragment() {


    private val viewModel : AuthViewModel by activityViewModels()

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
        initObserver()
    }

    private fun initObserver() {
        viewModel.stateMessage.observe(viewLifecycleOwner, Observer {
            it?.let { message ->
                printLogD("LoginFragment" , "This is new message $message")
                viewModel.clearAllStateMessages()
            }
        })
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
                loginIntent()
            }
        }
    }

    private fun loginIntent() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        printLogD("Loginfragment" , " Logging in with email : $email & password : $password")
        viewModel.setStateEvent(AuthStateEvent.LoginStateEvent(email,password))
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