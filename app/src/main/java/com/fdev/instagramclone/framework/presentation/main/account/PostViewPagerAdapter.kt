package com.fdev.instagramclone.framework.presentation.main.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.RequestManager
import com.fdev.instagramclone.R
import com.fdev.instagramclone.util.cutomview.NestedRecyclerGridLayoutManager
import com.fdev.instagramclone.util.printLogD
import kotlinx.android.synthetic.main.viewpager_post_item.view.*

class PostViewPagerAdapter(
        var requestManager: RequestManager,
        interaction: PhotoGridAdapter.Interaction,
        var viewPagerInteraction: Interaction,
        var onUpdate : PhotoGridAdapter.OnUpdate? = null,
        private val viewPool: RecyclerView.RecycledViewPool
) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    companion object{
        const val POST = 1
        const val TAGGED = 2
    }



    private var adapterList = ArrayList<PhotoGridAdapter>()


    private var postAdapter: PhotoGridAdapter

    private var recyclerViewList = ArrayList<RecyclerView>()



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
        return ViewPagerAdapterViewHolder(view , viewPagerInteraction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewPagerAdapterViewHolder -> {
                holder.bind(adapterList[position] , viewPool)
                recyclerViewList.add(position , holder.getView())
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

    fun getRecyclerView(index : Int) : RecyclerView{
        return recyclerViewList[index]
    }

    fun addOnUpdate(onUpdate: PhotoGridAdapter.OnUpdate){
        postAdapter.setOnUpdate(onUpdate)
        taggedAdapter.setOnUpdate(onUpdate)
    }



    class ViewPagerAdapterViewHolder
    constructor(
            itemView : View,
            var interaction: Interaction,

    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item : PhotoGridAdapter , viewPool: RecyclerView.RecycledViewPool) = with(itemView) {
            initRecylerView(itemView.post_recylerview , item , viewPool )
        }

        fun getView() : RecyclerView{
            return itemView.post_recylerview
        }

        private fun initRecylerView(recyclerView: RecyclerView, recyclerViewAdapter : PhotoGridAdapter,
                                    viewPool: RecyclerView.RecycledViewPool){
            recyclerView.apply{
                setRecycledViewPool(viewPool)
                val gridLayout = GridLayoutManager(context , 3)
                layoutManager = gridLayout
                setHasFixedSize(true)
                this.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        val layoutManager = recyclerView.layoutManager as GridLayoutManager
                        val lastPosition = layoutManager.findLastVisibleItemPosition()
                        printLogD("Adapter" , "$lastPosition : ${recyclerViewAdapter.itemCount.minus(1)} in $adapterPosition")
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
        fun afterScrolled(isFirstItem : Boolean)
    }


}
