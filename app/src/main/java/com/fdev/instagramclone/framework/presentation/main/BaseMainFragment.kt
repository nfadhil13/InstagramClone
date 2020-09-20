package com.fdev.instagramclone.framework.presentation.main


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fdev.instagramclone.framework.presentation.OnFragmentChangedListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.lang.ClassCastException


abstract class BaseMainFragment : Fragment(){

    lateinit var onFragmentChangedListener: OnFragmentChangedListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnFragmentChangedListener()
        onFragmentChangedListener.fragmentChanged(this)
    }

    fun setOnFragmentChangedListener(){
        activity?.let{
            if(it is MainActivity){
                try{
                    onFragmentChangedListener = context as OnFragmentChangedListener
                }catch (e : ClassCastException){
                    e.printStackTrace()
                }
            }
        }
    }


}