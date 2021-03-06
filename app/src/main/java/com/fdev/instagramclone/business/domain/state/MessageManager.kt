package com.fdev.instagramclone.business.domain.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.android.parcel.IgnoredOnParcel

const val MESSAGE_STACK_BUNDLE_KEY = "com.fdev.cleanarchitecture.business.domain.state"

class MessageManager{

    @IgnoredOnParcel
    private val _stateMessage: MutableLiveData<StateMessage?> = MutableLiveData()

    @IgnoredOnParcel
    val stateMessage: LiveData<StateMessage?>
        get() = _stateMessage

    fun isMessageEmpty(): Boolean{
        return stateMessage.value == null
    }



    fun add(newMessage: StateMessage): Boolean {
        if(isMessageEmpty()){
            setStateMessage(newMessage)
            return true
        }
        return false
    }

    fun removeCurrentMessage() {
        setStateMessage(null)

    }

    fun getCurrentMessasge()  = _stateMessage.value

    private fun setStateMessage(stateMessage: StateMessage?){
        _stateMessage.value = stateMessage
    }
}