package com.fdev.instagramclone.framework.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.Image
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.exifinterface.media.ExifInterface
import androidx.navigation.NavController
import com.fdev.instagramclone.util.BitmapUtils
import java.io.ByteArrayInputStream

fun TextView.changeTextcolor(
        @ColorRes color: Int
){
    context?.let{ context ->
        setTextColor(ContextCompat.getColor(context, color))
    }
}

fun ProgressBar.show(isDisplayed: Boolean){
    if(isDisplayed){
        visibility = View.VISIBLE
    }else{
        visibility = View.INVISIBLE
    }
}

fun NavController.popBackStackAllInstances(destination: Int, inclusive: Boolean) : Boolean{

    var popped : Boolean

    while(true){
        popped = popBackStack(destination, inclusive)
        if(!popped){
            break
        }

    }
    return popped

}

fun Image.toBitmap(width: Int, height: Int): Bitmap {
    val buffer = planes[0].buffer
    buffer.rewind()
    val bytes = ByteArray(buffer.capacity())
    buffer.get(bytes)
    val rotationAngle = rotateImage(bytes)
    val matrix = Matrix()
    matrix.postRotate(rotationAngle, width.toFloat(), height.toFloat())
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
    options.inSampleSize = BitmapUtils.calculateInSampleSize(options, width, height)
    options.inJustDecodeBounds = false
    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

fun rotateImage(bytes: ByteArray) : Float{
    val exifInterface = ExifInterface(ByteArrayInputStream(bytes))
    val orientation: Int = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)
    var rotationDegrees = 0f
    when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotationDegrees = 90f
        ExifInterface.ORIENTATION_ROTATE_180 -> rotationDegrees = 180f
        ExifInterface.ORIENTATION_ROTATE_270 -> rotationDegrees = 270f
    }

    return rotationDegrees
}

