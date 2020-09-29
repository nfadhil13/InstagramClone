package com.fdev.instagramclone.framework.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentMainBinding
import com.fdev.instagramclone.framework.presentation.main.chat.ChatListFragment
import com.fdev.instagramclone.framework.presentation.main.home.HomeFragment
import com.fdev.instagramclone.util.BOTTOM_NAV_BACKSTACK_KEY
import com.fdev.instagramclone.util.BottomNavController
import com.fdev.instagramclone.util.printLogD
import com.fdev.instagramclone.util.setUpNavigation

class MainFragment : Fragment() , BottomNavController.OnNavigationGraphChanged , BottomNavController.OnNavigationReselectedListener {

    private var _binding : FragmentMainBinding? = null

    private val binding
        get() = _binding!!


    lateinit var bottomNavController: BottomNavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this , object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(::bottomNavController.isInitialized){
                    bottomNavController.onBackPressed()
                }
            }

        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater , container , false)
        printLogD("MainFragment" , "onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBottomNavigation(savedInstanceState)


    }



    private fun setUpBottomNavigation(savedInstanceState: Bundle?) {
        context?.let {
            bottomNavController = BottomNavController(
                    this@MainFragment,
                    R.id.bottomnav_nav_host_containerr,
                    R.id.home_menu,
                    this
                    )
        }?: throw Exception("No Context")
        binding.bottomNav.apply{
            itemIconTintList = null
            setUpNavigation(bottomNavController , this@MainFragment)
            if (savedInstanceState == null) {
                bottomNavController.setupBottomNavigationBackStack(null)
                bottomNavController.onNavigationItemSelected()
            }
            else{
                (savedInstanceState[BOTTOM_NAV_BACKSTACK_KEY] as IntArray?)?.let { items ->
                    val backstack = BottomNavController.BackStack()
                    backstack.addAll(items.toTypedArray())
                    bottomNavController.setupBottomNavigationBackStack(backstack)
                }
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onGraphChange() {
        printLogD("MainFragment", "Test")
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putIntArray(BOTTOM_NAV_BACKSTACK_KEY, bottomNavController.navigationBackStack.toIntArray())
    }

    override fun onReselectNavItem(navController: NavController, menuItemId: Int) {
        when(menuItemId){

            R.id.home_menu -> {
                navController.navigate(R.id.homeFragment)
            }

            R.id.search_menu -> {
                navController.navigate(R.id.searchFragment)
            }

            R.id.add_photo_menu -> {
                navController.navigate(R.id.addPhotoFragment)
            }

            R.id.activity_menu -> {
                navController.navigate(R.id.activityFragment)
            }

            R.id.profile_menu -> {
                navController.navigate(R.id.profileFragment)
            }

            else -> {
                throw Exception("Error")
            }
        }
    }


}