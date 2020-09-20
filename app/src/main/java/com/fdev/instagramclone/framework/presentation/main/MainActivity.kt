package com.fdev.instagramclone.framework.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fdev.instagramclone.business.domain.state.UIComponentType
import com.fdev.instagramclone.databinding.ActivityMainBinding
import com.fdev.instagramclone.framework.presentation.BaseActivity
import com.fdev.instagramclone.framework.presentation.OnFragmentChangedListener
import com.fdev.instagramclone.framework.presentation.main.addphoto.AddPhotoFragment
import com.fdev.instagramclone.framework.presentation.main.chat.ChatListFragment
import com.fdev.instagramclone.framework.presentation.main.home.HomeFragment
import com.fdev.instagramclone.framework.presentation.show
import com.fdev.instagramclone.util.SessionManager
import com.fdev.instagramclone.util.printLogD
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() , OnFragmentChangedListener{

    private var _binding : ActivityMainBinding? = null



    private val binding
        get() = _binding!!

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        sessionManager.currentUser.observe(this, {
        })




        sessionManager.currentUser.observe(this, {
        })

        setViewPager()

    }

    private fun setViewPager(){
        binding.mainViewpager.apply{
            adapter = ScreenSlidePagerAdapter(this@MainActivity)
            offscreenPageLimit = 2
        }


    }

    override fun displayProgressBar(isDisplayed: Boolean) {
        binding.mainProgressbar.show(isDisplayed)
    }

    override fun onBackPressed() {

        binding.mainViewpager.let{
            if(it.isEnabled && it.currentItem>0){
                    it.currentItem = it.currentItem-1
            }else{
                super.onBackPressed()
            }
        }

    }


    private inner class ScreenSlidePagerAdapter(fragmentActivity :FragmentActivity)
        : FragmentStateAdapter(fragmentActivity){

        private val fragmentList = ArrayList<Fragment>()

        init{
            val homeFragment = MainFragment()

            val chatListFragment = ChatListFragment()

            fragmentList.add(homeFragment)
            fragmentList.add(chatListFragment)
        }

        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int) = fragmentList[position]

    }

    override fun fragmentChanged(fragment: Fragment) {
        binding.mainViewpager.isUserInputEnabled =
                fragment is HomeFragment || fragment is ChatListFragment
    }


}