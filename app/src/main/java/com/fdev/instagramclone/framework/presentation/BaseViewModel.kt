package com.fdev.instagramclone.framework.presentation

import androidx.lifecycle.*
import com.fdev.instagramclone.business.data.util.GenericErrors
import com.fdev.instagramclone.business.domain.state.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow




@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseViewModel<ViewState> : ViewModel()
{
    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()

    val dataChannelManager: DataChannelManager<ViewState>
            = object: DataChannelManager<ViewState>(){

        override fun handleNewData(data: ViewState) {
            this@BaseViewModel.handleNewData(data)
        }
    }

    val viewState: LiveData<ViewState>
        get() = _viewState

    val shouldDisplayProgressBar: LiveData<Boolean>
            = dataChannelManager.shouldDisplayProgressBar

    val stateMessage: LiveData<StateMessage?>
        get() = dataChannelManager.messageManager.stateMessage

    fun setupChannel() = dataChannelManager.setupChannel()

    abstract fun handleNewData(data: ViewState)

    abstract fun setStateEvent(stateEvent: StateEvent)

    fun emitStateMessageEvent(
            stateMessage: StateMessage,
            stateEvent: StateEvent
    ) = flow{
        emit(
                DataState.error<ViewState>(
                        response = stateMessage.response,
                        stateEvent = stateEvent
                )
        )
    }

    fun emitInvalidStateEvent(stateEvent: StateEvent) = flow {
        emit(
                DataState.error<ViewState>(
                        response = Response(
                                message = GenericErrors.INVALID_STATE_EVENT,
                                uiComponentType = UIComponentType.None(),
                                messageType = MessageType.Error()
                        ),
                        stateEvent = stateEvent
                )
        )
    }

    fun launchJob(
            stateEvent: StateEvent,
            jobFunction: Flow<DataState<ViewState>?>
    ) = dataChannelManager.launchJob(stateEvent, jobFunction)

    fun getCurrentViewStateOrNew(): ViewState{
        return viewState.value ?: initNewViewState()
    }

    fun setViewState(viewState: ViewState){
        _viewState.value = viewState
    }


    fun clearActiveStateEvents() = dataChannelManager.clearActiveStateEventCounter()

    fun clearAllStateMessages() = dataChannelManager.clearStateMessages()

    fun printStateMessages() = dataChannelManager.printCurrentStateMessages()

    fun cancelActiveJobs() = dataChannelManager.cancelJobs()

    abstract fun initNewViewState(): ViewState

}