package com.fdev.instagramclone.framework.presentation.main.addphoto

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentAddPhotoBinding
import com.fdev.instagramclone.framework.presentation.main.BaseMainFragment
import com.google.android.material.tabs.TabLayoutMediator
import java.io.File

const val PHOTO_BUNDLE_KEY = "photo"

class AddPhotoFragment : BaseMainFragment(R.layout.fragment_add_photo) , OnImageSelected  {

    private var _binding : FragmentAddPhotoBinding? = null

    private val binding
    get() = _binding!!

    private var tabMediator : TabLayoutMediator? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddPhotoBinding.inflate(layoutInflater , container , false)
        val view = binding.root
        return view
    }

    private val onPageChangeCallBack = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            if(position == 0){
                binding.btnMore.visibility = View.GONE
                binding.btnNext.visibility = View.GONE
            }else{
                binding.btnMore.visibility = View.VISIBLE
                binding.btnNext.visibility = View.VISIBLE
            }
        }
    }
    private val mode = listOf(
            "Photo",
            "Library"
    )
//    private val mode = listOf(
//            getString(R.string.photo_label),
//            getString(R.string.library_label)
//    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.apply {

            viewpager.apply {
                adapter = PhotoFragmentPagerAdapter(this@AddPhotoFragment , this@AddPhotoFragment)
                registerOnPageChangeCallback(onPageChangeCallBack)
                offscreenPageLimit = 2
            }

            tabMediator = TabLayoutMediator(takephotoTablayout , viewpager){ tab, position ->
                tab.text = mode[position]
            }

            tabMediator?.attach()

        }
    }

    private inner class PhotoFragmentPagerAdapter(fragment : Fragment , onImageSelected: OnImageSelected)
        : FragmentStateAdapter(fragment){

        private val fragmentList = ArrayList<Fragment>()

        init{
            val takePhotoFragment = TakePhotoFragment()

            takePhotoFragment.setOnImageSelected(onImageSelected)


            val photoDeviceFragment = PhotoDeviceFragment()

            fragmentList.add(takePhotoFragment)
            fragmentList.add(photoDeviceFragment)
        }

        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int) = fragmentList[position]

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewpager.adapter = null
        binding.viewpager.unregisterOnPageChangeCallback(onPageChangeCallBack)
        tabMediator?.detach()
        tabMediator = null
        _binding = null
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