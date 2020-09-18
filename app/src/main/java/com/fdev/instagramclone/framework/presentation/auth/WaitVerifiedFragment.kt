package com.fdev.instagramclone.framework.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fdev.instagramclone.R
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.business.domain.state.AreYouSureCallback
import com.fdev.instagramclone.business.domain.state.StateMessage
import com.fdev.instagramclone.business.domain.state.StateMessageCallback
import com.fdev.instagramclone.business.domain.state.UIComponentType
import com.fdev.instagramclone.databinding.FragmentWaitingVerifiedBinding
import com.fdev.instagramclone.framework.presentation.auth.state.AuthStateEvent
import com.fdev.instagramclone.util.printLogD
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

@FlowPreview
@ExperimentalCoroutinesApi
class WaitVerifiedFragment : BaseAuthFragment() {

    companion object {
        const val START_TIME = 200 //200 SECOND
        const val END_TIME = -1
    }

    private var _binding: FragmentWaitingVerifiedBinding? = null

    private val binding
        get() = _binding!!


    private var timer: Int = START_TIME

    private lateinit var timerJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupChannel()

        val callback = object : OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                uiController.basicUIInteraction(
                        "Your sing up will be cancled if you back",
                        UIComponentType.AreYouSureDialog(object : AreYouSureCallback {
                            override fun proceed() {
                                findNavController().navigate(R.id.action_waitVerifiedFragment_to_launcherFragment)
                            }

                            override fun cancel() {
                                //Do nothing
                            }

                        })
                )
                //Ask user if they are sure to get back , if sure than get back

            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWaitingVerifiedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initOnClickListener()
        initTimer()
    }

    private fun initTimer() {
        startTimer()
    }

    private fun startTimer() {
        timerJob = CoroutineScope(Dispatchers.Default).launch {
            timer = START_TIME
            while (timer >= END_TIME) {
                updateTimerUI()
                delay(1000)
                timer--
            }
        }

        timerJob.start()
    }

    private suspend fun updateTimerUI() {
        withContext(Main) {
            printLogD("WaitVerifiedFragment" , "send timer $timer")
            if(timer==-1){
                binding.resendCodeTimer.setText("")
            }else{
                binding.resendCodeTimer.setText("(${timer.toString()})")
            }

        }
    }

    override fun handleStateMessage(stateMessage: StateMessage, stateMessageCallback: StateMessageCallback) {
        uiController.onResponseReceived(
                stateMessage.response,
                stateMessageCallback
        )
    }


    private fun initObserver() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.waitVerifiedViewState?.let { waitVerifiedViewState ->
                printLogD("WaitVerifiedFragment", "user(${waitVerifiedViewState.verifiedUser?.email}) status isverified ${waitVerifiedViewState.userVerfiedStatus}")
                waitVerifiedViewState.verifiedUser?.let {
                    changUI(it)
                }
                if (waitVerifiedViewState.userVerfiedStatus) {
                    navToInputNamePassword()
                    waitVerifiedViewState.verifiedUser?.let {
                        viewModel.setsetEnterNamePasswordUser(it)
                    }
                    viewModel.setNewVerfiedUserToNull()
                }


            }
        })


    }

    private fun changUI(user: User) {
        binding.emailTv.setText(user.email)
    }

    private fun navToInputNamePassword() {
        findNavController().navigate(R.id.action_waitVerifiedFragment_to_inputNamePasswordFragment)
    }

    private fun initOnClickListener() {
        binding.apply {
            btnConfirmationNext.setOnClickListener {
                printLogD("WaitVerifiedFragment", "checking user ${viewModel.viewState.value?.waitVerifiedViewState?.verifiedUser ?: "User is null"}")
                val password =
                        viewModel.viewState.value?.waitVerifiedViewState?.tempPassword
                                ?: throw Exception("Unknown Error")
                viewModel.setStateEvent(AuthStateEvent.CheckUserVerifiedStateEvent(password))
            }

            resendCodeBtn.setOnClickListener {
                if (timer > END_TIME) {
                    uiController.basicUIInteraction(
                            "You can resend verification email after 200second",
                            UIComponentType.Toast()
                    )
                } else {
                    viewModel.setStateEvent(AuthStateEvent.ResendVerficationEmail())
                    startTimer()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (::timerJob.isInitialized) {
            if (timerJob.isActive) {
                timerJob.cancel(CancellationException("Cancel"))
            }
        }
        viewModel.viewState.value?.waitVerifiedViewState?.verifiedUser?.let {
            AuthStateEvent.DeleteUserStateEvent(it)
        }
        _binding = null
    }
}