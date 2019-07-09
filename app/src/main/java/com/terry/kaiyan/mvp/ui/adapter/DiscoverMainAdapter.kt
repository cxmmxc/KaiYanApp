package com.terry.kaiyan.mvp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.terry.kaiyan.mvp.ui.fragment.DiscoverCatgoryFragment
import com.terry.kaiyan.mvp.ui.fragment.DiscoverLikeFragment

/**
 * Author:ChenXinming
 * Date:2019/07/09
 * Description:
 */
class DiscoverMainAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DiscoverLikeFragment.newInstance()
            1 -> DiscoverCatgoryFragment.newInstance()
            else -> DiscoverLikeFragment.newInstance()
        }
    }
}
