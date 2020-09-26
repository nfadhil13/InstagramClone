package com.fdev.instagramclone.util.cutomview

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class SquareImageView(
        context: Context,
        attributeSet: AttributeSet? = null

): androidx.appcompat.widget.AppCompatImageView(context , attributeSet ){

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

}