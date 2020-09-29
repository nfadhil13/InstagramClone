package com.fdev.instagramclone.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Parcelable
import android.util.Log
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.fdev.instagramclone.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.parcel.Parcelize

/**
 * Class credit: Allan Veloso
 * I took the concept from Allan Veloso and made alterations to fit our needs.
 * https://stackoverflow.com/questions/50577356/android-jetpack-navigation-bottomnavigationview-with-youtube-or-instagram-like#_=_
 * @property navigationBackStack: Backstack for the bottom navigation
 */

const val BOTTOM_NAV_BACKSTACK_KEY =  "com.fdev.instagramclone.util.BottomNavController.bottom_nav_backstack"

class BottomNavController(
        var fragment: Fragment,
        @IdRes val containerId: Int,
        @IdRes val appStartDestinationId: Int,
        val graphChangeListener: OnNavigationGraphChanged?
) {
    private val TAG: String = "AppDebug"
    lateinit var navigationBackStack: BackStack
    lateinit var fragmentManager: FragmentManager
    lateinit var navItemChangeListener: OnNavigationItemChanged

    init {
        fragmentManager = fragment.childFragmentManager
    }

    fun setupBottomNavigationBackStack(previousBackStack: BackStack?){
        navigationBackStack = previousBackStack ?: BackStack.of(appStartDestinationId)
    }

    fun onNavigationItemSelected(menuItemId: Int = navigationBackStack.last()): Boolean {

        Log.d(TAG, "onNavigationItemSelected ")
        // Replace fragment representing a navigation item
        val fragment = fragmentManager.findFragmentByTag(menuItemId.toString())
                ?: createNavHost(menuItemId)
        fragmentManager.beginTransaction()
//                .setCustomAnimations(
//                        R.anim.fade_in,
//                        R.anim.fade_out,
//                        R.anim.fade_in,
//                        R.anim.fade_out
//                )
                .replace(containerId, fragment, menuItemId.toString())
                .addToBackStack(null)
                .commit()

        // Add to back stack
        navigationBackStack.moveLast(menuItemId)

        // Update checked icon
        navItemChangeListener.onItemChanged(menuItemId)

        // communicate with Activity
        graphChangeListener?.onGraphChange()

        return true
    }

    private fun createNavHost(menuItemId: Int): Fragment{
        return when(menuItemId){

            R.id.home_menu -> {
                NavHostFragment.create(R.navigation.home_nav)
            }

            R.id.search_menu -> {
                NavHostFragment.create(R.navigation.search_nav)
            }

            R.id.add_photo_menu -> {
                NavHostFragment.create(R.navigation.add_photo_nav)
            }

            R.id.activity_menu -> {
                NavHostFragment.create(R.navigation.activity_nav)
            }

            R.id.profile_menu -> {
                NavHostFragment.create(R.navigation.profile_nav)
            }

            else -> {
                throw Exception("Error")
            }
        }
    }

    @SuppressLint("RestrictedApi")
    fun onBackPressed() {
        val navController = fragmentManager.findFragmentById(containerId)!!
                .findNavController()

        when {
            navController.backStack.size > 2 ->{
                navController.popBackStack()
            }

            // Fragment back stack is empty so try to go back on the navigation stack
            navigationBackStack.size > 1 -> {
                Log.d(TAG, "logInfo: BNC: backstack size > 1")

                // Remove last item from back stack
                navigationBackStack.removeLast()

                // Update the container with new fragment
                onNavigationItemSelected()
            }
            // If the stack has only one and it's not the navigation home we should
            // ensure that the application always leave from startDestination
            navigationBackStack.last() != appStartDestinationId -> {
                Log.d(TAG, "logInfo: BNC: start != current")
                navigationBackStack.removeLast()
                navigationBackStack.add(0, appStartDestinationId)
                onNavigationItemSelected()
            }
            // Navigation stack is empty, so finish the activity
            else -> {
                Log.d(TAG, "logInfo: BNC: FINISH")
//                activity.finish()
            }
        }
    }

    @Parcelize
    class BackStack : ArrayList<Int>(), Parcelable {

        companion object {

            fun of(vararg elements: Int): BackStack {
                val b = BackStack()
                b.addAll(elements.toTypedArray())
                return b
            }
        }

        fun removeLast() = removeAt(size - 1)

        fun moveLast(item: Int) {
            remove(item) // if present, remove
            add(item) // add to end of list
        }

    }


    // For setting the checked icon in the bottom nav
    interface OnNavigationItemChanged {
        fun onItemChanged(itemId: Int)
    }

    // Execute when Navigation Graph changes.
    interface OnNavigationGraphChanged{
        fun onGraphChange()
    }

    interface OnNavigationReselectedListener{

        fun onReselectNavItem(navController: NavController, @MenuRes menuItemId: Int)
    }

    fun setOnItemNavigationChanged(listener: (itemId: Int) -> Unit) {
        this.navItemChangeListener = object : OnNavigationItemChanged {
            override fun onItemChanged(itemId: Int) {
                listener.invoke(itemId)
            }
        }
    }

}

// Convenience extension to set up the navigation
fun BottomNavigationView.setUpNavigation(
        bottomNavController: BottomNavController,
        onReselectListener: BottomNavController.OnNavigationReselectedListener
) {

    setOnNavigationItemSelectedListener {
        bottomNavController.onNavigationItemSelected(it.itemId)
    }

    setOnNavigationItemReselectedListener { menuItem ->
        bottomNavController
                .fragmentManager
                .findFragmentById(bottomNavController.containerId)!!
                .childFragmentManager
                .fragments[0]?.let { fragment ->

            onReselectListener.onReselectNavItem(
                    fragment.findNavController(),
                    menuItem.itemId
            )
        }
        bottomNavController.onNavigationItemSelected()
    }

    bottomNavController.setOnItemNavigationChanged { itemId ->
        menu.findItem(itemId).isChecked = true
    }
}