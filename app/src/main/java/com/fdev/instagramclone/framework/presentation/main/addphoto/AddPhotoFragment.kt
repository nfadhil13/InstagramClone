package com.fdev.instagramclone.framework.presentation.main.addphoto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentAddPhotoBinding
import com.fdev.instagramclone.databinding.FragmentPhotoGridBinding
import com.fdev.instagramclone.framework.presentation.main.BaseMainFragment
import com.fdev.instagramclone.framework.presentation.main.MainFragment
import com.fdev.instagramclone.framework.presentation.main.chat.ChatListFragment
import kotlinx.android.synthetic.main.fragment_add_photo.*

class AddPhotoFragment : BaseMainFragment(R.layout.fragment_add_photo){

    private val binding : FragmentAddPhotoBinding by viewBinding()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.apply {

            viewpager.apply {
                adapter = PhotoFragmentPagerAdapter(this@AddPhotoFragment)
            }


        }
    }

    private inner class PhotoFragmentPagerAdapter(fragment : Fragment)
        : FragmentStateAdapter(fragment){

        private val fragmentList = ArrayList<Fragment>()

        init{
            val takePhotoFragment = TakePhotoFragment()

            fragmentList.add(takePhotoFragment)
        }

        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int) = fragmentList[position]

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewpager.adapter = null

    }


}