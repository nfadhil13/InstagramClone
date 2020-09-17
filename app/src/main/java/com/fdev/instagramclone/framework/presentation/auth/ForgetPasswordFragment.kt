package com.fdev.instagramclone.framework.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fdev.instagramclone.R
import com.fdev.instagramclone.business.domain.state.StateMessage
import com.fdev.instagramclone.business.domain.state.StateMessageCallback
import com.fdev.instagramclone.business.interactors.auth.ResetPassword.Companion.EMAIL_SENT
import com.fdev.instagramclone.business.interactors.auth.ResetPassword.Companion.EMAIL_SENT_FAILED
import com.fdev.instagramclone.databinding.FragmentForgetPasswordBinding
import com.fdev.instagramclone.databinding.FragmentLoginBinding
import com.fdev.instagramclone.framework.presentation.auth.state.AuthStateEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class ForgetPasswordFragment : BaseAuthFragment() {


    private var _binding: FragmentForgetPasswordBinding? = null


    private val binding
        get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initClickListener()
    }

    override fun handleStateMessage(stateMessage: StateMessage, stateMessageCallback: StateMessageCallback) {
        when (stateMessage.response.message) {

            EMAIL_SENT -> {
                uiController.onResponseReceived(
                        response = stateMessage.response,
                        stateMessageCallback
                )
            }

            EMAIL_SENT_FAILED -> {
                uiController.onResponseReceived(
                        response = stateMessage.response,
                        stateMessageCallback
                )
            }

            else -> {
                uiController.onResponseReceived(
                        response = stateMessage.response,
                        stateMessageCallback
                )
            }

        }
    }

    private fun initObserver() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.forgetPasswordViewState?.let { forgetPasswordViewState ->
                if (forgetPasswordViewState.isSuccess) {
                    navToLauncher()
                    viewModel.setForgetPasswordStateToNull()
                }
            }
        })
    }

    private fun navToLauncher() {
        findNavController().navigate(R.id.action_forgetPasswordFragment_to_launcherFragment)
    }

    private fun initClickListener() {
        binding.apply {
            this.btnForgetPasswordNext.setOnClickListener {
                sentForgetEmailIntent(emailEditText.text.toString())
            }
        }
    }

    private fun sentForgetEmailIntent(email: String) {
        viewModel.setStateEvent(AuthStateEvent.ForgetPassword(email))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}