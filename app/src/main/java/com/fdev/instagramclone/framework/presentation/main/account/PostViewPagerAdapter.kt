package com.fdev.instagramclone.framework.presentation.main.account

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.RequestManager
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.ViewpagerPostItemBinding
import com.fdev.instagramclone.util.printLogD
import kotlinx.android.synthetic.main.viewpager_post_item.view.*

class PostViewPagerAdapter(
        var requestManager: RequestManager,
        interaction: PhotoGridAdapter.Interaction,
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
//        val binding = ViewpagerPostItemBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,false
//        )
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.viewpager_post_item,
                parent,
                false
        )
        return ViewPagerAdapterViewHolder(view , onNextPageInteraction)
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
            var itemView : View,
            var interaction: Interaction,
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item : PhotoGridAdapter) = with(itemView) {
            initRecylerView(itemView.post_recylerview , item)
        }

        private fun initRecylerView(recyclerView: RecyclerView, recyclerViewAdapter : PhotoGridAdapter){
            recyclerView.apply{
                val gridLayout = GridLayoutManager( context, 3)
                layoutManager = gridLayout
                setHasFixedSize(true)
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

                addOnScrollListener(object : RecyclerView.OnScrollListener(){
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        interaction.afterScrolled(gridLayout.findFirstCompletelyVisibleItemPosition() == 0)
                    }
                })


                adapter = recyclerViewAdapter
            }
        }

    }

    interface Interaction {
        fun onNextPage(type : Int)
        fun afterScrolled(isFirstItem : Boolean)
    }


}
