package com.terry.kaiyan.mvp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.terry.kaiyan.mvp.ui.fragment.*

/**
 * Author:ChenXinming
 * Date:2019/06/21
 * Description:
 */
class HomeAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 ->
                DailyFragment.newInstance()
            1 ->
                DiscoverFragment.newInstance()
            2 ->
                HotFragment.newInstance()
            3 ->
                MineFragment.newInstance()
            else ->
                DailyFragment.newInstance()
        }
    }


    override fun getItemCount(): Int {
        return 4
    }
}
