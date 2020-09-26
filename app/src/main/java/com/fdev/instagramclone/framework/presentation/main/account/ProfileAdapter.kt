package com.fdev.instagramclone.framework.presentation.main.account

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.RequestManager
import com.fdev.instagramclone.R
import com.fdev.instagramclone.business.domain.model.Post
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.databinding.EmptyItemBinding
import com.fdev.instagramclone.databinding.ProfileProfileHeaderBinding
import com.fdev.instagramclone.databinding.ProfileProfilePostBinding
import com.fdev.instagramclone.framework.datasource.network.implementation.UserFirestoreServiceImpl
import com.fdev.instagramclone.util.printLogD
import com.google.android.material.tabs.TabLayoutMediator

class ProfileAdapter(
        private val requestManager: RequestManager,
        private val interaction: Interaction,
        viewPagerInteraction: PostViewPagerAdapter.Interaction,
        photoGridInteraction: PhotoGridAdapter.Interaction,
) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    private val HEADER = 1
    private val CONTENT = 2

    // Cause i still looking a way to interact to the inner recyclerview , so i used this way to trigger next page
    private val FOOTER = 3

    private var user: User

    private val postViewPagerAdapter: PostViewPagerAdapter

//    private val viewPool = RecyclerView.RecycledViewPool()

//    private val post : List<Post> = ArrayList()
//
//    private val tagged : List<Post> = ArrayList()


    init {

        this.user = User()

        postViewPagerAdapter = PostViewPagerAdapter(requestManager,
                photoGridInteraction,
                viewPagerInteraction,
                null)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {

            HEADER -> {
                val binding = ProfileProfileHeaderBinding.inflate(
                        LayoutInflater.from(
                                parent.context
                        ), parent, false
                )

                return ProfileHeaderViewHolder(binding, requestManager)
            }

            CONTENT -> {
                val binding = ProfileProfilePostBinding.inflate(
                        LayoutInflater.from(
                                parent.context
                        ), parent, false
                )

                return ProfileContentViewHolder(binding , interaction)
            }

            FOOTER -> {
                val binding = EmptyItemBinding.inflate(
                        LayoutInflater.from(
                                parent.context
                        ), parent, false
                )

                return FooterViewHolder(binding)
            }

            else -> {
                throw Exception("Unvalid viewtype")
            }

        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProfileHeaderViewHolder -> {
                holder.bind(user)
            }

            is ProfileContentViewHolder -> {
                holder.bind(postViewPagerAdapter)
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun getItemViewType(position: Int): Int {
        //Cause the flag using
        return position + 1
    }

    fun setNewUser(newUser: User) {
        if (!newUser.isSame(user)) {
            user = newUser
            notifyItemChanged(0)
        }
    }

    fun addItemToTagged(list: List<String>) {
        postViewPagerAdapter.addItemToTagged(list)
    }

    fun addItemToPost(list: List<String>) {
        postViewPagerAdapter.addItemToPost(list)
    }


    class ProfileHeaderViewHolder
    constructor(
            private var binding: ProfileProfileHeaderBinding,
            private var requestManager: RequestManager
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) = with(binding) {
            followersTv.text = user.followers.size.toString()

            followingTv.text = user.following.size.toString()

            bioTv.text = user.bio

            fullnameTv.text = user.name

            if (!user.profileImage.equals(UserFirestoreServiceImpl.USER_DEFAULT_PICTURE_URL)) {
                (requestManager as RequestManager)
                        .load(user.profileImage)
                        .into(profileCircleimageview)
            }
        }
    }

    class ProfileContentViewHolder
    constructor(
            private var binding: ProfileProfilePostBinding,
            private var interaction: Interaction
    ) : RecyclerView.ViewHolder(binding.root), PhotoGridAdapter.OnUpdate {

        private val tabDrawable = listOf(R.drawable.ic_baseline_grid_on_24, R.drawable.ic_baseline_account_box_24)

        private lateinit var adapter: PostViewPagerAdapter

        fun bind(postViewPagerAdapter: PostViewPagerAdapter) = with(binding) {

            postViewPagerAdapter.addOnUpdate(this@ProfileContentViewHolder)

            adapter = postViewPagerAdapter

            binding.contentViewpager.adapter = postViewPagerAdapter

            TabLayoutMediator(binding.contentTabLayout, binding.contentViewpager) { tab, position ->
                tab.setIcon(tabDrawable[position])
            }.attach()


            binding.contentViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    interaction.onPageChanged(position)
                    val view = postViewPagerAdapter.getRecyclerView(binding.contentViewpager.currentItem)
                    view.post {
                        val wMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
                        val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                        view.measure(wMeasureSpec, hMeasureSpec)

                        if (binding.contentViewpager.layoutParams.height != view.measuredHeight) {
                            // ParentViewGroup is, for example, LinearLayout
                            // ... or whatever the parent of the ViewPager2 is
                            binding.contentViewpager.layoutParams = (binding.contentViewpager.layoutParams)
                                    .also { lp -> lp.height = view.measuredHeight }
                        }
                    }
                }
            })

        }

        override fun onUpdate() {
            println("onupdate")
            val view = adapter.getRecyclerView(binding.contentViewpager.currentItem)
            view.post {
                val wMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
                val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                view.measure(wMeasureSpec, hMeasureSpec)

                if (binding.contentViewpager.layoutParams.height != view.measuredHeight) {
                    // ParentViewGroup is, for example, LinearLayout
                    // ... or whatever the parent of the ViewPager2 is
                    binding.contentViewpager.layoutParams = (binding.contentViewpager.layoutParams)
                            .also { lp -> lp.height = view.measuredHeight }
                }
            }
        }
    }


    class FooterViewHolder
    constructor(
            private var binding: EmptyItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) = with(binding) {

        }
    }


    interface Interaction {
        fun onPageChanged(page : Int)
    }
}
