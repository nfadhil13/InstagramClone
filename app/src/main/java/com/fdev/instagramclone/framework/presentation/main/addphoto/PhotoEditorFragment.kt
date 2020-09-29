package com.fdev.instagramclone.framework.presentation.main.addphoto

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentPhotoEditorBinding
import com.fdev.instagramclone.framework.presentation.main.BaseMainFragment
import com.fdev.instagramclone.util.printLogD

class PhotoEditorFragment : BaseMainFragment(R.layout.fragment_photo_editor){

    private val binding : FragmentPhotoEditorBinding by viewBinding()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let{
            val image = it.get(PHOTO_BUNDLE_KEY)
            if(image is Bitmap){
                Glide.with(requireContext())
                        .load(image)
                        .into(binding.photoEditorImageview)
            }else{
                printLogD("PhotoEditorFragment" , "Image is not bitmap")
            }
        }?:   printLogD("PhotoEditorFragment" , "Argument is null")



    }


}