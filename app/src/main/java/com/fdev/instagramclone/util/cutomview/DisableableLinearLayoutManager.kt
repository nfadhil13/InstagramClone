package com.fdev.instagramclone.util.cutomview

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class DisableableLinearLayoutManager(
        context : Context? = null
) : LinearLayoutManager(context){

    var canScrollVertically = true

    override fun canScrollVertically(): Boolean {
        return canScrollVertically
    }

}