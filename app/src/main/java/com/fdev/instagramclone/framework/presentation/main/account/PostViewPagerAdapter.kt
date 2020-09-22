package com.fdev.instagramclone.framework.presentation.main.account

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.RequestManager
import com.fdev.instagramclone.databinding.ViewpagerPostItemBinding

class PostViewPagerAdapter(
        context: Context,
        var requestManager: RequestManager,
        var interaction: PhotoGridAdapter.Interaction,
        var onNextPageInteraction: Interaction
) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object{
        const val POST = 1
        const val TAGGED = 2
    }

    private var adapterList = ArrayList<PhotoGridAdapter>()


    private var postAdapter: PhotoGridAdapter



    private var taggedAdapter: PhotoGridAdapter

    init {

        postAdapter = PhotoGridAdapter(
                requestManager,
                interaction,
                AdapterType.Post
        )






        taggedAdapter = PhotoGridAdapter(
                requestManager,
                interaction,
                AdapterType.Tagged
        )



        adapterList.add(postAdapter)
        adapterList.add(taggedAdapter)



    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ViewpagerPostItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false
        )
        return ViewPagerAdapterViewHolder(binding , onNextPageInteraction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewPagerAdapterViewHolder -> {
                holder.bind(adapterList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }

    fun addItemToTagged(list : List<String>) {
        taggedAdapter.preloadGlideImages(
                requestManager,
                list
        )
        taggedAdapter.submitList(list)
    }

    fun addItemToPost(list : List<String>) {
        postAdapter.preloadGlideImages(
                requestManager,
                list
        )
        postAdapter.submitList(list)
    }



    class ViewPagerAdapterViewHolder
    constructor(
            var binding : ViewpagerPostItemBinding,
            var interaction: Interaction
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item : PhotoGridAdapter) = with(binding) {
            initRecylerView(binding.postRecylerview , item)
        }

        private fun initRecylerView(recyclerView: RecyclerView, recyclerViewAdapter : PhotoGridAdapter){
            recyclerView.apply{
                layoutManager = GridLayoutManager(context , 3)
                isNestedScrollingEnabled = false
                addOnScrollListener(object : RecyclerView.OnScrollListener(){
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                        val lastPosition = layoutManager.findLastVisibleItemPosition()
                        if(lastPosition == recyclerViewAdapter.itemCount.minus(1)){
                            interaction.onNextPage(adapterPosition)
                        }
                    }
                })

                adapter = recyclerViewAdapter
            }
        }

    }

    interface Interaction {
        fun onNextPage(type : Int)
    }

}
