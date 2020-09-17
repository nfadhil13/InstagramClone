package com.fdev.instagramclone.framework.presentation.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fdev.instagramclone.R
import com.fdev.instagramclone.business.domain.state.StateMessage
import com.fdev.instagramclone.business.domain.state.StateMessageCallback
import com.fdev.instagramclone.databinding.FragmentSignupBinding
import com.fdev.instagramclone.framework.presentation.auth.state.AuthStateEvent
import com.fdev.instagramclone.util.printLogD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class SignupFragment : BaseAuthFragment() {

    private var _binding: FragmentSignupBinding? = null

    private val binding
        get() = _binding!!



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = FragmentSignupBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupChannel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initClickListner()
    }

    override fun handleStateMessage(stateMessage: StateMessage, stateMessageCallback: StateMessageCallback) {
        uiController.onResponseReceived(
                stateMessage.response,
                stateMessageCallback
        )
    }

    private fun initObserver() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {viewState->
            printLogD("Signupfragment" , "New viewstate ${viewState.signUpViewState}")
            viewState.signUpViewState?.let{signUpViewState ->

                signUpViewState.email?.let{
                    binding.emailEditText.setText(it)
                }

                if(signUpViewState.succesUser != null && signUpViewState.tempPassword != null){
                    viewModel.setNewVerfiedUser(signUpViewState.succesUser!!, signUpViewState.tempPassword!!)
                    viewModel.setSignUpViewStatetoNull()
                    navToWaitVerified()
                }
            }

        })

    }

    private fun initClickListner() {
        binding.apply {

            loginBtnTv.setOnClickListener{
                navToLogin()
            }

            btnSignupNext.setOnClickListener {
                //Do signup thing
                signupIntent()

            }

            loginMethodEmailTv.setOnClickListener {
                setTOEmailMode()
            }

            loginMethodPhoneTv.setOnClickListener {
                setToNumberMode()
            }
        }
    }


    private fun signupIntent() {
        binding.apply {
            viewModel.setStateEvent(AuthStateEvent.SignupStateEvent(emailEditText.text.toString()))
        }
    }

    private fun navToLogin() {
        findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
    }

    private fun navToWaitVerified() {
        findNavController().navigate(R.id.action_signupFragment_to_waitVerifiedFragment)
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
        saveSignUpfields()
        _binding = null
    }

    private fun saveSignUpfields(){
        viewModel.setSignupField(binding.emailEditText.text.toString())
    }


}