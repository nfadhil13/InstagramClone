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
import com.fdev.instagramclone.databinding.FragmentWaitingVerifiedBinding
import com.fdev.instagramclone.framework.presentation.auth.state.AuthStateEvent
import com.fdev.instagramclone.util.printLogD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class WaitVerifiedFragment : Fragment() {

    private var _binding: FragmentWaitingVerifiedBinding? = null

    private val viewModel : AuthViewModel by activityViewModels()

    private val binding
        get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupChannel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWaitingVerifiedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        printLogD("WaitVerifiedFragment" , viewModel.viewState.value?.signUpViewState?.tempPassword ?: "Null")
        initObserver()
        initOnClickListener()
    }

    private fun initObserver() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {viewState->
            viewState.waitVerifiedViewState?.let{waitVerifiedViewState ->
                printLogD("WaitVerifiedFragment" , "user status isverified ${waitVerifiedViewState.userVerfiedStatus}}")
                if(waitVerifiedViewState.userVerfiedStatus){
                    navToInputNamePassword()
                }
            }
        })


    }

    private fun navToInputNamePassword() {
        findNavController().navigate(R.id.action_waitVerifiedFragment_to_inputNamePasswordFragment)
    }

    private fun initOnClickListener() {
        binding.apply {
            btnConfirmationNext.setOnClickListener {
                printLogD("WaitVerifiedFragment" , "checking user ${viewModel.viewState.value?.waitVerifiedViewState?.verifiedUser ?: "User is null"}")
                val password =
                        viewModel.viewState.value?.waitVerifiedViewState?.tempPassword ?: throw Exception("Unknown Error")
                viewModel.setStateEvent(AuthStateEvent.CheckUserVerifiedStateEvent(password))
            }
        }
    }

//    private fun navToInputNamePassword() {
//        findNavController().navigate(R.id.action_waitVerifiedFragment_to_inputNamePasswordFragment)
//    }

    override fun onDetach() {
        super.onDetach()
        viewModel.viewState.value?.waitVerifiedViewState?.verifiedUser?.let{
            AuthStateEvent.DeleteUserStateEvent(it)
        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}