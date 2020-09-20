package com.fdev.instagramclone.framework.presentation.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.fdev.instagramclone.R
import com.fdev.instagramclone.business.data.cache.CacheErrors
import com.fdev.instagramclone.business.data.network.NetworkErrors
import com.fdev.instagramclone.business.data.network.NetworkResult
import com.fdev.instagramclone.business.domain.state.StateMessage
import com.fdev.instagramclone.business.domain.state.StateMessageCallback
import com.fdev.instagramclone.business.interactors.auth.SyncAndGetLastUser
import com.fdev.instagramclone.databinding.ActivityAuthBinding
import com.fdev.instagramclone.databinding.FragmentSplashBinding
import com.fdev.instagramclone.framework.presentation.auth.state.AuthStateEvent
import com.fdev.instagramclone.util.cutomview.isInternetAvailable
import com.fdev.instagramclone.util.printLogD
import com.google.android.gms.common.util.ArrayUtils.contains
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main


@FlowPreview
@ExperimentalCoroutinesApi
class SplashFragment : BaseAuthFragment() {


    private var _binding: FragmentSplashBinding? = null


    private val binding
        get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        context?.let {
            sycnIntent(it)
        } ?: throw Exception("Error")
    }

    private fun sycnIntent(context: Context) {
        printLogD("SplashFragment", "Syncing data")
        viewModel.setStateEvent(AuthStateEvent.SycnAndGetLasUser(isInternetAvailable(context)))
    }

    override fun handleStateMessage(stateMessage: StateMessage, stateMessageCallback: StateMessageCallback) {
        stateMessage.response.message?.let { message ->

            when {
                message.contains(CacheErrors.CACHE_DATA_NULL)
                        || message.contains(NetworkErrors.NETWORK_DATA_NULL) -> {
                    navToLauncher()
                }

            }

            stateMessageCallback.removeMessageFromStack()
        }
    }


    private fun initObserver() {
        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            viewState.syncAndGetLastUser?.let { syncAndGetLastUser ->
                syncAndGetLastUser.lastUser?.let {
                    viewModel.logIn(it)
                }
            }
        })
    }

    private fun navToLauncher() {
        findNavController().navigate(R.id.action_splashFragment_to_launcherFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}