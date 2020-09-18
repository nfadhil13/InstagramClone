package com.fdev.instagramclone.framework.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fdev.instagramclone.R
import com.fdev.instagramclone.business.domain.state.StateMessage
import com.fdev.instagramclone.business.domain.state.StateMessageCallback
import com.fdev.instagramclone.databinding.FragmentLoginBinding
import com.fdev.instagramclone.framework.presentation.auth.state.AuthStateEvent
import com.fdev.instagramclone.framework.presentation.changeTextcolor
import com.fdev.instagramclone.util.cutomview.EmailEditTextCallback
import com.fdev.instagramclone.util.printLogD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class LoginFragment : BaseAuthFragment() {



    private var _binding: FragmentLoginBinding? = null

    private val binding
        get() = _binding!!



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initClickListener()
        initUI()

    }

    private fun initUI() {
        binding.emailEditText.addOnInvalidCallBack(object : EmailEditTextCallback {
            override fun onInvalidEmailInput() {
                binding.emailWarnTv.let{
                    it.text = getString(R.string.invalid_email_field_warn)
                    it.changeTextcolor(R.color.warnColor)
                }

            }

            override fun onEmptyInput() {
                binding.emailWarnTv.let{
                    it.text = getString(R.string.empty_field_warn)
                    it.changeTextcolor(R.color.warnColor)
                }
            }


            override fun onvalidInput() {
                binding.emailWarnTv.let{
                    binding.emailWarnTv.let{
                        it.text = ""
                        it.changeTextcolor(R.color.blackLine)
                    }
                }
            }

        })

        binding.passwordEditText.textView = binding.passwordWarnTv
    }



    override fun handleStateMessage(stateMessage: StateMessage, stateMessageCallback: StateMessageCallback) {
        uiController.onResponseReceived(
                stateMessage.response,
                stateMessageCallback
        )
    }

    private fun initObserver() {
        viewModel.viewState.observe(viewLifecycleOwner , Observer {viewState->
            printLogD("Loginfragment" , "New viewstate ${viewState.loginViewState}")
            viewState.loginViewState?.let{
                it.email?.let{lastEmail ->
                    binding.emailEditText.setText(lastEmail)
                }

                it.password?.let{lastPassword->
                    binding.passwordEditText.setText(lastPassword)
                }

                it.succesUser?.let{ succesUser ->
                    viewModel.logIn(succesUser)
                }
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
                if(emailEditText.validateEmail() && passwordEditText.isPasswordValid()){
                    loginIntent()
                }
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
        saveLoginfields()
        _binding = null
    }

    private fun saveLoginfields() {
        binding.apply {
            viewModel.setLoginField(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
            )
        }
    }

}