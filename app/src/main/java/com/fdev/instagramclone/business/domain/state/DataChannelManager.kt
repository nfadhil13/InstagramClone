package com.fdev.instagramclone.business.domain.state

import com.fdev.instagramclone.util.printLogD
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
abstract class DataChannelManager<ViewState> {

    private var channelScope: CoroutineScope? = null
    private val stateEventManager: StateEventManager = StateEventManager()

    val messageManager = MessageManager()

    val shouldDisplayProgressBar = stateEventManager.shouldDisplayProgressBar

    fun setupChannel(){
        cancelJobs()
    }

    abstract fun handleNewData(data: ViewState)

    fun launchJob(
            stateEvent: StateEvent,
            jobFunction: Flow<DataState<ViewState>?>
    ){
        if(canExecuteNewStateEvent(stateEvent)){
            printLogD("DCM", "launching job: ${stateEvent.eventName()}")
            addStateEvent(stateEvent)
            jobFunction
                    .onEach { dataState ->
                        dataState?.let { nonNullDataState ->
                            withContext(Main){
                                nonNullDataState.data?.let { data ->
                                    handleNewData(data)
                                }
                                nonNullDataState.stateMessage?.let { stateMessage ->
                                    handleNewStateMessage(stateMessage)
                                }
                                nonNullDataState.stateEvent?.let { stateEvent ->
                                    removeStateEvent(stateEvent)
                                }
                            }
                        }
                    }
                    .launchIn(getChannelScope())
        }
    }

    private fun canExecuteNewStateEvent(stateEvent: StateEvent): Boolean{
        // If a job is already active, do not allow duplication
        if(isJobAlreadyActive(stateEvent)){
            return false
        }
        // if a dialog is showing, do not allow new StateEvents
        if(!isMessageStackEmpty()){
            return false
        }
        return true
    }

    fun isMessageStackEmpty(): Boolean {
        return messageManager.isMessageEmpty()
    }

    private fun handleNewStateMessage(stateMessage: StateMessage){
        appendStateMessage(stateMessage)
    }

    private fun appendStateMessage(stateMessage: StateMessage) {
        messageManager.add(stateMessage)
    }

    fun clearStateMessage(){
        printLogD("DataChannelManager", "clear state message")
        messageManager.removeCurrentMessage()
    }

    fun clearStateMessages() = messageManager.removeCurrentMessage()

    fun printCurrentStateMessages(){
        printLogD("DCM", "${messageManager.getCurrentMessasge()}")

    }

    // for debugging
    fun getActiveJobs() = stateEventManager.getActiveJobNames()

    fun clearActiveStateEventCounter()
            = stateEventManager.clearActiveStateEventCounter()

    fun addStateEvent(stateEvent: StateEvent)
            = stateEventManager.addStateEvent(stateEvent)

    fun removeStateEvent(stateEvent: StateEvent?)
            = stateEventManager.removeStateEvent(stateEvent)

    private fun isStateEventActive(stateEvent: StateEvent)
            = stateEventManager.isStateEventActive(stateEvent)

    fun isJobAlreadyActive(stateEvent: StateEvent): Boolean {
        return isStateEventActive(stateEvent)
    }

    fun getChannelScope(): CoroutineScope {
        return channelScope?: setupNewChannelScope(CoroutineScope(IO))
    }

    private fun setupNewChannelScope(coroutineScope: CoroutineScope): CoroutineScope{
        channelScope = coroutineScope
        return channelScope as CoroutineScope
    }

    fun cancelJobs(){
        if(channelScope != null){
            if(channelScope?.isActive == true){
                channelScope?.cancel()
            }
            channelScope = null
        }
        clearActiveStateEventCounter()
    }

}