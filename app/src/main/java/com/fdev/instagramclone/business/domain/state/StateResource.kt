package com.fdev.instagramclone.business.domain.state



data class StateMessage(val response: Response)

data class Response(
        val message: String?,
        val uiComponentType: UIComponentType = UIComponentType.None(),
        val messageType: MessageType
)

sealed class UIComponentType{

    class Toast: UIComponentType()

    class Dialog: UIComponentType()

//    class AreYouSureDialog(
//            val callback: AreYouSureCallback
//    ): UIComponentType()
//
    class SnackBar: UIComponentType()

    class None: UIComponentType()
}

sealed class MessageType{

    class Success: MessageType()

    class Error: MessageType()

    class Info: MessageType()

    class None: MessageType()
}
