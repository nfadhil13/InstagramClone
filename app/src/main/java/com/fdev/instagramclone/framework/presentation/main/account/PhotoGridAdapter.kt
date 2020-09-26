package com.fdev.instagramclone.framework.presentation.main.account

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.RequestManager
import com.fdev.instagramclone.databinding.PhotoGridItemContainerBinding
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.fdev.instagramclone.util.printLogD


class PhotoGridAdapter(
        private val requestManager: RequestManager,
        private val interaction: Interaction? = null,
        private val type : AdapterType,
) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onUpdate : OnUpdate?= null

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(
                    PhotoGridChangeCallBack(),
                    AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
            )

    init{

    }

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
                holder.bind(differ.currentList[position])
            }
        }
    }



    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<String>) {
        val callback = Runnable {
            printLogD("PhotoGridAdapter" , "callback : ${differ.currentList.size}")
            onUpdate?.onUpdate()
        }
        differ.submitList(list.toMutableList() , callback)
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

    fun setOnUpdate(newOnUpdate : OnUpdate){
      onUpdate   = newOnUpdate
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

    interface OnUpdate {
        fun onUpdate()
    }


    internal inner class PhotoGridChangeCallBack(
    ) : ListUpdateCallback {

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            printLogD("PhotoGridChangeCallBack", "onChanged")
            this@PhotoGridAdapter.notifyItemRangeChanged(position, count, payload)
        }

        override fun onInserted(position: Int, count: Int) {
            printLogD("PhotoGridChangeCallBack", "OnInserted")
            this@PhotoGridAdapter.notifyItemRangeChanged(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            printLogD("PhotoGridChangeCallBack", "onMoved")
            this@PhotoGridAdapter.notifyDataSetChanged()
        }

        override fun onRemoved(position: Int, count: Int) {
            printLogD("PhotoGridChangeCallBack", "onRemoved")
            this@PhotoGridAdapter.notifyDataSetChanged()
        }
    }
}

enum class AdapterType {
    Tagged, Post
}
