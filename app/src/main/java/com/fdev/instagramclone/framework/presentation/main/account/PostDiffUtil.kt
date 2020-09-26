package com.fdev.instagramclone.framework.presentation.main.account

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil

class PostDiffUtil(private val oldList: List<String>, private val newList: List<String>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].equals(newList[newItemPosition])
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return  oldList[oldPosition].equals(newList)
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}