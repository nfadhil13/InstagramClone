package com.fdev.instagramclone.framework.presentation.main


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.fdev.instagramclone.framework.presentation.OnFragmentChangedListener
import com.fdev.instagramclone.framework.presentation.popBackStackAllInstances
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.lang.ClassCastException


abstract class BaseMainFragment(
        @LayoutRes layoutId : Int
) : Fragment(
        layoutId
) {

    lateinit var onFragmentChangedListener: OnFragmentChangedListener

    lateinit var activityContext: Context

    var isNavigated = false

    fun navigateWithAction(action: NavDirections) {
        isNavigated = true
        findNavController().navigate(action)
    }

    fun navigate(resId: Int) {
        isNavigated = true
        findNavController().navigate(resId)
    }

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

    override fun onDestroyView() {
        super.onDestroyView()
//        if (!isNavigated){
//            requireActivity().onBackPressedDispatcher.addCallback(this){
//                val navController = findNavController()
//                if(navController.currentBackStackEntry?.destination)
//            }
//        }
    }


}