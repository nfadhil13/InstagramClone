package com.fdev.instagramclone.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(
        var space : Int
) : RecyclerView.ItemDecoration(){

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if(parent.getChildAdapterPosition(view) == state.itemCount.minus(1)){
            outRect.left = space
            outRect.right = 0
        }else{
            outRect.left = 0
            outRect.right = space

        }
    }
}