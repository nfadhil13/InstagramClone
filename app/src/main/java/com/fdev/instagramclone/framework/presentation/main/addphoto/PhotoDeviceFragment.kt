package com.fdev.instagramclone.framework.presentation.main.addphoto

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentPhotoDeviceBinding
import com.fdev.instagramclone.framework.presentation.main.BaseMainFragment


class PhotoDeviceFragment : BaseMainFragment(R.layout.fragment_photo_device){

    private var _binding : FragmentPhotoDeviceBinding? = null

    private val binding
    get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding  = FragmentPhotoDeviceBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        getImages()
    }


    private fun initUI() {
        binding.apply {
            btnMode.setOnClickListener {
                if(squareImageView.scaleType == ImageView.ScaleType.CENTER_CROP){
                    squareImageView.scaleType = ImageView.ScaleType.CENTER
                }else{
                    squareImageView.scaleType = ImageView.ScaleType.CENTER_CROP
                }
            }
        }
        initRecyclerView()
    }


    private fun initRecyclerView() {
//        binding.photoRecyclerview.apply {
//            layoutManager = GridLayoutManager(context, 5)
//
//        }
    }

    private fun getImages() {
        val resolver = requireContext().contentResolver
        val files = requireContext().externalMediaDirs
        for(file in files){
            println(file.extension)
        }
    }


}