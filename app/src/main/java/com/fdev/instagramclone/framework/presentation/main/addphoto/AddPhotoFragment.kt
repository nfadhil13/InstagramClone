package com.fdev.instagramclone.framework.presentation.main.addphoto

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentAddPhotoBinding
import com.fdev.instagramclone.databinding.FragmentPhotoGridBinding
import com.fdev.instagramclone.framework.presentation.main.BaseMainFragment
import com.fdev.instagramclone.framework.presentation.main.MainFragment
import com.fdev.instagramclone.framework.presentation.main.chat.ChatListFragment
import kotlinx.android.synthetic.main.fragment_add_photo.*
import java.io.File

const val PHOTO_BUNDLE_KEY = "photo"

class AddPhotoFragment : BaseMainFragment(R.layout.fragment_add_photo) , OnImageSelected{

    private val binding : FragmentAddPhotoBinding by viewBinding()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.apply {

            viewpager.apply {
                adapter = PhotoFragmentPagerAdapter(this@AddPhotoFragment , this@AddPhotoFragment)
            }


        }
    }

    private inner class PhotoFragmentPagerAdapter(fragment : Fragment , onImageSelected: OnImageSelected)
        : FragmentStateAdapter(fragment){

        private val fragmentList = ArrayList<Fragment>()

        init{
            val takePhotoFragment = TakePhotoFragment()

            takePhotoFragment.setOnImageSelected(onImageSelected)

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

    override fun onImageSelected(bitmap: Bitmap) {
        val bundle = bundleOf(PHOTO_BUNDLE_KEY to bitmap )
        findNavController().navigate(R.id.action_addPhotoFragment3_to_photoEditorFragment , bundle)
    }

    override fun onImageSelected(file: File) {

    }

    override fun onError() {

    }


}