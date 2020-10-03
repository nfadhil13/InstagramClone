package com.fdev.instagramclone.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.provider.MediaStore


object BitmapUtils {

    fun getBitMapFromAssets(context: Context
                            , filename : String
                            , width : Int
                            , height : Int) : Bitmap?{
        val assetManager = context.assets

        var bitmap : Bitmap?  = null
        try{
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            val inputStream = assetManager.open(filename)
            options.inSampleSize = calculateInSampleSize(options , width , height)
            options.inJustDecodeBounds = false
            bitmap =  BitmapFactory.decodeStream(inputStream , null , options)
        }catch (e : Exception){
            e.printStackTrace()
        }
        return bitmap
    }


    fun applyOverlay(context: Context, sourceImage: Bitmap, overlayDrawableResourceId: Int): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val width = sourceImage.width
            val height = sourceImage.height
            val r = context.resources
            val imageAsDrawable: Drawable = BitmapDrawable(r, sourceImage)
            val layers = arrayOfNulls<Drawable>(2)
            layers[0] = imageAsDrawable
            layers[1] = BitmapDrawable(r, decodeSampledBitmapFromResource(r, overlayDrawableResourceId, width, height))
            val layerDrawable = LayerDrawable(layers)
            bitmap = drawableToBitmap(layerDrawable)
        } catch (ex: Exception) {
        }
        return bitmap
    }

    fun decodeSampledBitmapFromResource(res: Resources?, resId: Int,
                                        reqWidth: Int, reqHeight: Int): Bitmap {

        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

    fun calculateInSampleSize(
            options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight
                    && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap? {
        var bitmap: Bitmap? = null
        if (drawable is BitmapDrawable) {
            val bitmapDrawable = drawable
            if (bitmapDrawable.bitmap != null) {
                return bitmapDrawable.bitmap
            }
        }
        bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Single color bitmap will be created of 1x1 pixel
        } else {
            Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}