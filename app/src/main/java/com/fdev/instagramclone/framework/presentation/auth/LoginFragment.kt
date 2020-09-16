package com.fdev.instagramclone.framework.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentLoginBinding
import com.fdev.instagramclone.framework.presentation.auth.state.AuthStateEvent
import com.fdev.instagramclone.util.printLogD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class LoginFragment : Fragment() {


    private val viewModel : AuthViewModel by activityViewModels()

    private var _binding: FragmentLoginBinding? = null

    private val binding
        get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupChannel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initClickListener()

    }

    private fun initObserver() {
        viewModel.viewState.observe(viewLifecycleOwner , Observer {viewState->
            viewState.loginViewState?.succesUser?.let {
                printLogD("Loginfragment" , "Succes Login with ${it.email}")
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