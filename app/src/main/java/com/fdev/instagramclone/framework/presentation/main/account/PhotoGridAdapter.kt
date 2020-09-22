package com.fdev.instagramclone.framework.presentation.main.account

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import com.fdev.instagramclone.databinding.PhotoGridItemContainerBinding
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade


class PhotoGridAdapter(
        private val requestManager: RequestManager,
        private val interaction: Interaction? = null,
        private val type : AdapterType
) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = PhotoGridItemContainerBinding.inflate(
                LayoutInflater.from(
                        parent.context
                ),parent , false
        )
        return PhotoGridViewHolder(itemBinding , interaction , requestManager , type)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PhotoGridViewHolder-> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<String>) {
        differ.submitList(list)
    }

    //To preload the image and cache it
    fun preloadGlideImages(
            requestManager: RequestManager ,
            imagesURL : List<String>
    ){
        for(url in imagesURL){
            requestManager
                    .load(url)
                    .preload()
        }
    }

    class PhotoGridViewHolder
    constructor(
            private val photoGridBinding: PhotoGridItemContainerBinding,
            private val interaction: Interaction?,
            private val requestManager: RequestManager,
            private val type : AdapterType
    ) : RecyclerView.ViewHolder(photoGridBinding.root) {

        fun bind(item: String) = with(photoGridBinding) {
            postImage.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item , type)
            }

            requestManager
                    .load(item)
                    .transition(withCrossFade())
                    .into(postImage)

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: String, type : AdapterType)
    }
}

enum class AdapterType {
    Tagged, Post
}
