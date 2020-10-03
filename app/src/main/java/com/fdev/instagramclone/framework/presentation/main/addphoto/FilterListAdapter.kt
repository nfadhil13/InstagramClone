package com.fdev.instagramclone.framework.presentation.main.addphoto

import android.app.Activity
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FilterItemBinding
import com.fdev.instagramclone.framework.presentation.changeTextcolor
import com.zomato.photofilters.FilterPack
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.utils.ThumbnailItem
import com.zomato.photofilters.utils.ThumbnailsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FilterListAdapter(
        var requestManager: RequestManager,
        private val interaction: Interaction? = null
) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var selectedFilter = -1

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ThumbnailItem>() {

        override fun areItemsTheSame(oldItem: ThumbnailItem, newItem: ThumbnailItem): Boolean {
            return oldItem.filterName == newItem.filterName
        }

        override fun areContentsTheSame(oldItem: ThumbnailItem, newItem: ThumbnailItem): Boolean {
            return oldItem.filterName == newItem.filterName
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = FilterItemBinding.inflate(
                LayoutInflater.from(
                        parent.context
                ), parent, false
        )
        return FilterListViewHolder(
                binding,
                interaction,
                requestManager
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FilterListViewHolder -> {
                holder.bind(differ.currentList.get(position)) {
                    this@FilterListAdapter.notifyItemChanged(selectedFilter)
                    selectedFilter = position
                }
                if(selectedFilter == position){
                    holder.syncTextColor(isSelected = true)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<ThumbnailItem>) {
        differ.submitList(list)
    }

    class FilterListViewHolder
    constructor(
            var binding: FilterItemBinding,
            private val interaction: Interaction?,
            var requestManager: RequestManager
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ThumbnailItem, itemSelected: (() -> Unit)? = null) = with(binding) {
            itemView.setOnClickListener {
                itemSelected?.invoke()
                interaction?.onItemSelected(item.filter)
                syncTextColor(isSelected = true)
            }
            filterName.changeTextcolor(
                    R.color.blurLine
            )
            filterName.text = item.filterName
            requestManager
                    .load(item.image)
                    .into(binding.postImage)


        }

        fun syncTextColor(isSelected : Boolean) {

            @ColorRes
            val textColor = if(isSelected) R.color.blackLine else R.color.blurLine

            binding.filterName.changeTextcolor(
                    textColor
            )
        }
    }

    interface Interaction {
        fun onItemSelected(filter : Filter)
    }
}
