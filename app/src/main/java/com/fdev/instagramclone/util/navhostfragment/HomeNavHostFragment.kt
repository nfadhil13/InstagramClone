//package com.fdev.instagramclone.util.navhostfragment
//
//import android.content.Context
//import android.os.Bundle
//import androidx.annotation.NavigationRes
//import androidx.navigation.fragment.NavHostFragment
//
//class HomeNavHostFragment : NavHostFragment() {
//
//    override fun onAttach(context: Context) {
////        childFragmentManager.fragmentFactory =
////                (activity as MainActivity).accountFragmentFactory
//        super.onAttach(context)
//    }
//
//    companion object {
//        const val KEY_GRAPH_ID =
//                "android-support-nav:fragment:graphId"
//
//        @JvmStatic
//        fun create(
//                @NavigationRes graphId: Int = 0
//        ): SearchNavHostFragment {
//            var bundle: Bundle? = null
//            if (graphId != 0) {
//                bundle = Bundle()
//                bundle.putInt(KEY_GRAPH_ID, graphId)
//            }
//            val result =
//                    SearchNavHostFragment()
//            if (bundle != null) {
//                result.arguments = bundle
//            }
//            return result
//        }
//    }
//
//}