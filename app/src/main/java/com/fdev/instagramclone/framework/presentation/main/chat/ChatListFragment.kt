package com.fdev.instagramclone.framework.presentation.main.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fdev.instagramclone.databinding.FragmentChatListBinding
import com.fdev.instagramclone.framework.presentation.main.BaseMainFragment
import com.fdev.instagramclone.util.printLogD

class ChatListFragment : BaseMainFragment(){

    private var _binding : FragmentChatListBinding? = null

    private val binding
        get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChatListBinding.inflate(inflater ,container , false)
            printLogD("Chatlist" , "onCreateView")
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}