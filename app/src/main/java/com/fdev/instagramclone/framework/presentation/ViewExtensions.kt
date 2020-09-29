package com.fdev.instagramclone.framework.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.fdev.instagramclone.R

fun TextView.changeTextcolor(
        @ColorRes color : Int
){
    context?.let{context ->
        setTextColor(ContextCompat.getColor(context , color))
    }
}

fun ProgressBar.show(isDisplayed: Boolean){
    if(isDisplayed){
        visibility = View.VISIBLE
    }else{
        visibility = View.INVISIBLE
    }
}

fun NavController.popBackStackAllInstances(destination : Int , inclusive : Boolean) : Boolean{

    var popped : Boolean

    while(true){
        popped = popBackStack(destination , inclusive)
        if(!popped){
            break
        }

    }
    return popped

}

fun Image.toBitmap(): Bitmap {
    val buffer = planes[0].buffer
    buffer.rewind()
    val bytes = ByteArray(buffer.capacity())
    buffer.get(bytes)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}