package com.fdev.instagramclone.framework.presentation.auth

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.fdev.instagramclone.business.domain.state.StateMessage
import com.fdev.instagramclone.business.domain.state.StateMessageCallback
import com.fdev.instagramclone.framework.presentation.UIController
import com.fdev.instagramclone.util.printLogD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.lang.ClassCastException

@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseAuthFragment : Fragment(){

    lateinit var uiController: UIController

    lateinit var stateMessageCallback : StateMessageCallback

    val viewModel : AuthViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupChannel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUIController()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateMessageCallback= object : StateMessageCallback{
            override fun removeMessageFromStack() {
                viewModel.clearAllStateMessages()
            }

        }
        subscribeCommon()
    }

    private fun subscribeCommon() {
        viewModel.shouldDisplayProgressBar.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            uiController.displayProgressBar(it)
        })

        viewModel.stateMessage.observe(viewLifecycleOwner , androidx.lifecycle.Observer {
            it?.let{stateMessage ->
                printLogD("BaseAuthFragment", "$stateMessage")
                handleStateMessage(stateMessage , stateMessageCallback)
            }

        })
    }

    abstract fun handleStateMessage(stateMessage: StateMessage , stateMessageCallback: StateMessageCallback)

    fun setUIController(){
        activity?.let{
            if(it is AuthActivity){
                try{
                    uiController = context as UIController
                }catch (e : ClassCastException){
                    e.printStackTrace()
                }
            }
        }
    }
}