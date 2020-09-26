package com.fdev.instagramclone.framework.presentation.main.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.fdev.instagramclone.R
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.databinding.FragmentProfileBinding
import com.fdev.instagramclone.framework.presentation.main.BaseMainFragment
import com.fdev.instagramclone.util.cutomview.DisableableLinearLayoutManager
import com.fdev.instagramclone.util.printLogD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProfileFragment : BaseMainFragment(R.layout.fragment_profile),
        ProfileAdapter.Interaction,
        PostViewPagerAdapter.Interaction, PhotoGridAdapter.Interaction {



    private var currentPage = 1


    private var _binding: FragmentProfileBinding? = null


    private val binding
        get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    private lateinit var profileAdapter : ProfileAdapter

    private lateinit var requestManager: RequestManager


    private var images1 = listOf(
            "https://www.worldwomanfoundation.com/wp-content/uploads/2018/09/Jeannette_Ceja-_Head_Shot_2018_0-770x330.jpg",
            "https://womensagenda.com.au/wp-content/uploads/2020/05/Sarah-Hill-002-1024x683.jpeg",
            "https://news.harvard.edu/wp-content/uploads/2020/06/Durba_Mitra-copy_2500-1200x800.jpg",
            "https://images.idgesg.net/images/article/2020/01/women-in-it_daphne-jones-100828118-large.jpg",
            "https://vtnews.vt.edu/content/vtnews_vt_edu/en/articles/2020/04/science-women-in-data-science-online/_jcr_content/article-image.transform/m-medium/image.jpg",

            )

    private var images2 = mutableListOf(
            "https://koreanindo.net/wp-content/uploads/2019/02/everglow-wang-yiren.jpg",
            "https://kepoper.com/wp-content/uploads/2019/10/Mina-Twice-Wiki-1-758x620.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQT1uidd0fswlshWotS43XDLyscw6ZtFw-E3Q&usqp=CAU",
            "https://cdn.idntimes.com/content-images/community/2020/04/20200417-142524-373be8fe5157e9ca9a24a8951ab21dda.jpg",
            "https://i.redd.it/21csbponb0551.jpg",
            "https://i.pinimg.com/originals/24/5d/87/245d87d800391ff353c04aed8eb52a50.jpg",
            "https://womensagenda.com.au/wp-content/uploads/2020/05/Sarah-Hill-002-1024x683.jpeg",
            "https://news.harvard.edu/wp-content/uploads/2020/06/Durba_Mitra-copy_2500-1200x800.jpg",
            "https://images.idgesg.net/images/article/2020/01/women-in-it_daphne-jones-100828118-large.jpg",
            "https://koreanindo.net/wp-content/uploads/2019/02/everglow-wang-yiren.jpg",
            "https://kepoper.com/wp-content/uploads/2019/10/Mina-Twice-Wiki-1-758x620.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQT1uidd0fswlshWotS43XDLyscw6ZtFw-E3Q&usqp=CAU",
            "https://cdn.idntimes.com/content-images/community/2020/04/20200417-142524-373be8fe5157e9ca9a24a8951ab21dda.jpg",
            "https://i.redd.it/21csbponb0551.jpg",
            "https://i.pinimg.com/originals/24/5d/87/245d87d800391ff353c04aed8eb52a50.jpg",
            "https://womensagenda.com.au/wp-content/uploads/2020/05/Sarah-Hill-002-1024x683.jpeg",
            "https://news.harvard.edu/wp-content/uploads/2020/06/Durba_Mitra-copy_2500-1200x800.jpg",
            "https://images.idgesg.net/images/article/2020/01/women-in-it_daphne-jones-100828118-large.jpg"
            )


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater , container , false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initUI()
        profileAdapter.addItemToPost(images2)
        profileAdapter.addItemToTagged(images1)
    }

    private fun initUI() {
        binding.apply {
            initRecyclerView(profileMainRecyclerview)
            usernameTv.setOnClickListener {
                printLogD("AddItem" , "before :${images2.size}")
                images2.addAll(images1)
                profileAdapter.addItemToPost(images2)
                printLogD("AddItem" , "after :${images2.size}")
            }


            btnMore.setOnClickListener {
                profileAdapter.addItemToPost(images1)
            }
        }
    }

    private fun initRecyclerView(profileMainRecyclerview: RecyclerView) {
        initGlide()
        profileAdapter = ProfileAdapter(
            requestManager,
                this,
                this,
                this
        )
        profileMainRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@ProfileFragment.context)
            setHasFixedSize(true)
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                    if(lastPosition == profileAdapter.itemCount.minus(1)){
                            onNextPage(currentPage)
                    }
                }

            })

            adapter = profileAdapter
        }
    }

    private fun initGlide() {
        val requestOptions = RequestOptions
                .placeholderOf(R.drawable.default_image)
                .error(R.drawable.ic_baseline_error_outline_24)

        activity?.let {
            requestManager = Glide.with(it)
                    .applyDefaultRequestOptions(requestOptions)
        }
    }

    private fun initObserver() {
        viewModel.sessionManager.currentUser.observe(viewLifecycleOwner, { user ->
            user?.let {
                profileAdapter.setNewUser(user)
                binding.usernameTv.text = user.username
            }
        })
    }


    override fun onItemSelected(position: Int, item: String, type: AdapterType) {
        when (type) {

            AdapterType.Post -> {
                Toast.makeText(context, "Post $position", Toast.LENGTH_SHORT).show()
            }


            AdapterType.Tagged -> {
                Toast.makeText(context, "Tagged $position", Toast.LENGTH_SHORT).show()
            }

        }
    }


    fun onNextPage(type: Int) {
        printLogD("AddItem" , "before :${images2.size}")
        images2.addAll(images1)
        profileAdapter.addItemToPost(images2)
        printLogD("AddItem" , "after :${images2.size}")
    }

    override fun onPause() {
        super.onPause()
        binding.swipeRefreshLayout.isEnabled = false
    }


    override fun afterScrolled(isFirstItem: Boolean) {
        binding.swipeRefreshLayout.isEnabled = isFirstItem
        printLogD("ProfileFragment" , "$isFirstItem")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.profileMainRecyclerview.adapter = null
        _binding = null
    }

    override fun onPageChanged(page: Int) {
        currentPage = page
    }

}