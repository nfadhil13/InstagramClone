package com.fdev.instagramclone.framework.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fdev.instagramclone.R
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.business.domain.state.*
import com.fdev.instagramclone.databinding.FragmentInputNamePasswordBinding
import com.fdev.instagramclone.framework.presentation.auth.state.AuthStateEvent
import com.fdev.instagramclone.util.printLogD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@ExperimentalCoroutinesApi
class InputNamePasswordFragment() : BaseAuthFragment(){


    private var _binding: FragmentInputNamePasswordBinding? = null


    private lateinit var currentUser : User

    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = object : OnBackPressedCallback(true){

            override fun handleOnBackPressed() {
                uiController.basicUIInteraction(
                        "Your sing up will be cancled if you back" ,
                        UIComponentType.AreYouSureDialog(object : AreYouSureCallback {
                            override fun proceed() {
                                findNavController().navigate(R.id.action_inputNamePasswordFragment_to_launcherFragment)
                            }

                            override fun cancel() {
                                //Do nothing
                            }

                        })
                )
                //Ask user if they are sure to get back , if sure than get back

            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(this,callback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentInputNamePasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initClickListener()
        initUI()
    }

    private fun initUI() {
        binding.apply {
            passwordEditText.textView = passwordWarnTv
            usernameEditText.warnTextView = usernameWarnTv
        }
    }

    override fun handleStateMessage(stateMessage: StateMessage, stateMessageCallback: StateMessageCallback) {
        uiController.onResponseReceived(
                stateMessage.response,
                stateMessageCallback
        )
    }

    private fun initObserver() {
        viewModel.viewState.observe(viewLifecycleOwner , Observer {viewState->
            viewState.inputNamePasswordViewState?.let {enterNamePasswordViewState ->
                enterNamePasswordViewState.user?.let{user->
                    printLogD("InputNamePasswordFragment" , "Signup ${user.isRegistered}")
                    currentUser = user
                    if(user.isRegistered){
                        viewModel.logIn(user)
                        //Log in in the session manager
                    }
                }
            }
        })
    }

    private fun initClickListener() {
        binding.apply {
            btnSignupNext.setOnClickListener {
                if(::currentUser.isInitialized){
                    if(passwordEditText.isPasswordValid() && usernameEditText.isValid()){
                        val name = nameEditText.text.toString()
                        val password = passwordEditText.text.toString()
                        val username = usernameEditText.text.toString()

                        currentUser.name = name
                        currentUser.username = username
                        currentUser.password = password
                        inputNamePasswordIntent(currentUser)
                    }
                }
            }
        }
    }

    private fun inputNamePasswordIntent(user : User){
        viewModel.setStateEvent(AuthStateEvent.InputNamePassword(user))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}