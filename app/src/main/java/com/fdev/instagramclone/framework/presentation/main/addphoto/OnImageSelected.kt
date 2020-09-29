package com.fdev.instagramclone.framework.presentation.main.addphoto

import android.graphics.Bitmap
import java.io.File

interface OnImageSelected {

    fun onImageSelected(bitmap: Bitmap)

    fun onImageSelected(file : File)

    fun onError()

}