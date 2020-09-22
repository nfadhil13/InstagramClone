package com.fdev.instagramclone.framework.presentation.main.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.RequestManager
import com.fdev.instagramclone.R
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.databinding.FragmentProfileBinding
import com.fdev.instagramclone.framework.datasource.network.implementation.UserFirestoreServiceImpl
import com.fdev.instagramclone.framework.presentation.main.BaseMainFragment
import com.fdev.instagramclone.util.printLogD
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProfileFragment : BaseMainFragment(), PhotoGridAdapter.Interaction, PostViewPagerAdapter.Interaction {

    companion object {
        const val PAGE_NUMBER = 2
    }

    private var _binding: FragmentProfileBinding? = null


    private val viewModel: ProfileViewModel by viewModels()

    lateinit var postViewPagerAdapter: PostViewPagerAdapter

    private val images = ArrayList<String>()

    private val images2 = ArrayList<String>()

    private val tabDrawable = listOf(R.drawable.ic_baseline_grid_on_24, R.drawable.ic_baseline_account_box_24)

    @Inject
    lateinit var requestManager: RequestManager

    private val binding
        get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        for(i in 0..10){
            images.add("https://i.stack.imgur.com/cdCSj.jpg")
        }
        initUI()
    }


    private fun initUI() {
        initViewPager()
        postViewPagerAdapter.addItemToPost(images)
        postViewPagerAdapter.addItemToTagged(images)
    }

    private fun initViewPager() {
        context?.let {
            postViewPagerAdapter = PostViewPagerAdapter(it, requestManager, this, this)
        } ?: throw Exception("Fail to get context")

        binding.contentViewpager.adapter = postViewPagerAdapter

        TabLayoutMediator(binding.contentTabLayout , binding.contentViewpager){ tab , position ->
            tab.setIcon(tabDrawable[position])
        }.attach()


    }

    private fun initObserver() {
        viewModel.sessionManager.currentUser.observe(viewLifecycleOwner, { user ->
            user?.let {
                setUI(it)
            }
        })
    }


    private fun setUI(user: User) {
        binding.apply {

            usernameTv.text = user.username

            followersTv.text = user.followers.size.toString()

            followingTv.text = user.following.size.toString()

            bioTv.text = user.bio

            fullnameTv.text = user.name

            if (!user.profileImage.equals(UserFirestoreServiceImpl.USER_DEFAULT_PICTURE_URL)) {
                //Glide things
            }


        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemSelected(position: Int, item: String, type : AdapterType) {
        when(type){

            AdapterType.Post -> {
               Toast.makeText(context , "Post $position", Toast.LENGTH_SHORT).show()
            }


            AdapterType.Tagged -> {
                Toast.makeText(context , "Tagged $position", Toast.LENGTH_SHORT).show()
            }

        }
    }


    override fun onNextPage(type: Int) {

    }


}