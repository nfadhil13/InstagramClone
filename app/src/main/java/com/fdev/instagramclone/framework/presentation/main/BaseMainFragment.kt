package com.fdev.instagramclone.framework.presentation.main


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fdev.instagramclone.framework.presentation.OnFragmentChangedListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.lang.ClassCastException


abstract class BaseMainFragment : Fragment() {

    lateinit var onFragmentChangedListener: OnFragmentChangedListener

    lateinit var activityContext: Context

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnFragmentChangedListener()
        onFragmentChangedListener.fragmentChanged(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
    }

    fun setOnFragmentChangedListener() {
        activity?.let {
            try {
                onFragmentChangedListener = activityContext as OnFragmentChangedListener
            } catch (e: ClassCastException) {
                e.printStackTrace()
            }

        }
    }


}