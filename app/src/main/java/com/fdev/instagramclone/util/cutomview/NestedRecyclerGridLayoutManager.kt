package com.fdev.instagramclone.util.cutomview

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class NestedRecyclerGridLayoutManager : GridLayoutManager {

    constructor(context: Context?  , spanCount : Int) : super(context ,spanCount) {}
    constructor(context: Context?, spanCount : Int ,orientation: Int, reverseLayout: Boolean) : super(context , spanCount, orientation, reverseLayout) {}

    private val mMeasuredDimension = IntArray(2)
    override fun onMeasure(recycler: RecyclerView.Recycler, state: RecyclerView.State, widthSpec: Int, heightSpec: Int) {
        val widthMode: Int = View.MeasureSpec.getMode(widthSpec)
        val heightMode: Int = View.MeasureSpec.getMode(heightSpec)
        val widthSize: Int = View.MeasureSpec.getSize(widthSpec)
        val heightSize: Int = View.MeasureSpec.getSize(heightSpec)
        var width = 0
        var height = 0
        for (i in 0 until itemCount) {
            measureScrapChild(recycler, i, View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                    mMeasuredDimension)
            if (orientation == HORIZONTAL) {
                width = width + mMeasuredDimension[0]
                if (i == 0) {
                    height = mMeasuredDimension[1]
                }
            } else {
                height = height + mMeasuredDimension[1]
                if (i == 0) {
                    width = mMeasuredDimension[0]
                }
            }
        }
        when (widthMode) {
            View.MeasureSpec.EXACTLY -> width = widthSize
            View.MeasureSpec.AT_MOST, View.MeasureSpec.UNSPECIFIED -> {
            }
        }
        when (heightMode) {
            View.MeasureSpec.EXACTLY -> height = heightSize
            View.MeasureSpec.AT_MOST, View.MeasureSpec.UNSPECIFIED -> {
            }
        }
        setMeasuredDimension(width, height)
    }

    private fun measureScrapChild(recycler: RecyclerView.Recycler, position: Int, widthSpec: Int,
                                  heightSpec: Int, measuredDimension: IntArray) {
        try {
            val view: View? = recycler.getViewForPosition(0) //fix IndexOutOfBoundsException
            if (view != null) {
                val p = view.getLayoutParams() as RecyclerView.LayoutParams
                val childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
                        paddingLeft + paddingRight, p.width)
                val childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                        paddingTop + paddingBottom, p.height)
                view.measure(childWidthSpec, childHeightSpec)
                measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin
                measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin
                recycler.recycleView(view)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}