package com.terry.kaiyan.utils

import androidx.annotation.NonNull
import androidx.annotation.RestrictTo
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.*
import com.google.android.material.tabs.TabLayout
import com.jess.arms.utils.LogUtils
import java.lang.IllegalStateException
import java.lang.ref.WeakReference
import java.lang.reflect.Method

/**
 * Author:ChenXinming
 * Date:2019/07/09
 * Description:
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
open class TabLayoutMediator @JvmOverloads constructor(
    @NonNull tabLayout: TabLayout, @NonNull viewPager2: ViewPager2, autoRefresh: Boolean,
    @NonNull onConfigureTabCallback: OnConfigureTabCallback
) {
    private var mAttached: Boolean = false
    private var mTabLayout: TabLayout = tabLayout
    private var mViewPager2: ViewPager2 = viewPager2
    private var mAutoRefresh = autoRefresh
    private lateinit var mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>

    private var mTabLayoutOnPageChangeCallback: TabLayoutOnPageChangeCallback? = null
    private var mOnTabSelectedListener: ViewPagerOnTabSelectListener? = null
    private var mPagerAdapterObserver: RecyclerView.AdapterDataObserver? = null
    private var mOnConfigureTabCallback: OnConfigureTabCallback = onConfigureTabCallback

    private var sSetScrollPosition: Method? = null
    private var sSelectTab: Method? = null

    init {
        sSetScrollPosition = TabLayout::class.java.getDeclaredMethod(
            "setScrollPosition",
            Int::class.java,
            Float::class.java,
            Boolean::class.java,
            Boolean::class.java
        )
        sSetScrollPosition?.isAccessible = true
        sSelectTab =
            TabLayout::class.java.getDeclaredMethod("selectTab", TabLayout.Tab::class.java, Boolean::class.java)
        sSelectTab?.isAccessible = true
        LogUtils.debugInfo("cxm", "init")
    }


    public interface OnConfigureTabCallback {
        fun onConfigureTab(@NonNull tab: TabLayout.Tab, position: Int)
    }

    public fun attach() {
        if (mAttached) {
            return
        }
        mAdapter = mViewPager2.adapter!!
        mAttached = true
        mTabLayoutOnPageChangeCallback = TabLayoutOnPageChangeCallback(mTabLayout)
        mViewPager2.registerOnPageChangeCallback(mTabLayoutOnPageChangeCallback!!)
        mOnTabSelectedListener = ViewPagerOnTabSelectListener(mViewPager2)
        mTabLayout.addOnTabSelectedListener(mOnTabSelectedListener!!)
        if (mAutoRefresh) {
            //PageObserver
            mPagerAdapterObserver = AdapterDataObserver()
            mAdapter.registerAdapterDataObserver(mPagerAdapterObserver!!)
        }
        populateTabsFromPageAdapter()
        LogUtils.debugInfo("cxm", "attach")
        mTabLayout.setScrollPosition(mViewPager2.currentItem, 0F, true)
    }

    fun populateTabsFromPageAdapter() {
        mTabLayout.removeAllTabs()
        mAdapter?.let {
            var itemCount = it.itemCount
            for (index in 0 until itemCount) {
                var tab = mTabLayout.newTab()
                mOnConfigureTabCallback.onConfigureTab(tab, index)
                mTabLayout.addTab(tab, false)
            }

            if (itemCount > 0) {
                var currentItem = mViewPager2.currentItem
                if (currentItem != mTabLayout.selectedTabPosition) {
                    mTabLayout.getTabAt(currentItem)?.select()
                }
            }

        }
    }

    fun setScrollPosition(
        tabLayout: TabLayout,
        position: Int,
        positionOffset: Float,
        updateSelectText: Boolean,
        updateIndicatorPosition: Boolean
    ) {
        sSetScrollPosition?.invoke(tabLayout, position, positionOffset, updateSelectText, updateIndicatorPosition)


    }

    fun selectPosition(tabLayout: TabLayout, tab: TabLayout.Tab?, select: Boolean) {
        sSelectTab?.invoke(tabLayout, tab, select)
    }

    private fun throwMethodNotFound() {
        throw IllegalStateException("Method Not Found")
    }

    inner class AdapterDataObserver : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            populateTabsFromPageAdapter()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            populateTabsFromPageAdapter()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
            populateTabsFromPageAdapter()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            populateTabsFromPageAdapter()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            super.onItemRangeChanged(positionStart, itemCount)
            populateTabsFromPageAdapter()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            super.onItemRangeChanged(positionStart, itemCount, payload)
            populateTabsFromPageAdapter()
        }
    }

    inner class TabLayoutOnPageChangeCallback : OnPageChangeCallback {

        private var mTabLayoutRef: WeakReference<TabLayout>
        private var mPreScrollState: Int? = SCROLL_STATE_IDLE
        private var mScrollState: Int? = SCROLL_STATE_IDLE

        constructor(tabLayout: TabLayout) {
            mTabLayoutRef = WeakReference(tabLayout)
            reset()
        }

        private fun reset() {
            mPreScrollState = SCROLL_STATE_IDLE
            mScrollState = SCROLL_STATE_IDLE
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            mPreScrollState = mScrollState
            mScrollState = state
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            var tabLayout = mTabLayoutRef.get()
            tabLayout?.let {
                val updateText = mScrollState != SCROLL_STATE_SETTLING || mPreScrollState == SCROLL_STATE_DRAGGING
                val updateIndicator = !(mScrollState == SCROLL_STATE_SETTLING && mPreScrollState == SCROLL_STATE_IDLE)
                setScrollPosition(tabLayout, position, positionOffset, updateText, updateIndicator)
            }
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            var tabLayout = mTabLayoutRef.get()
            tabLayout?.let {
                if (it.selectedTabPosition != position && position < tabLayout.tabCount) {
                    var updateIndicator =
                        mScrollState == SCROLL_STATE_IDLE || (mScrollState == SCROLL_STATE_SETTLING && mPreScrollState == SCROLL_STATE_IDLE)
                    selectPosition(tabLayout, tabLayout.getTabAt(position), updateIndicator)
                }
            }
        }
    }

    inner class ViewPagerOnTabSelectListener : TabLayout.OnTabSelectedListener {

        constructor(viewPager2: ViewPager2) {
            viewPager = viewPager2
        }

        private var viewPager: ViewPager2? = null


        override fun onTabReselected(p0: TabLayout.Tab?) {

        }

        override fun onTabUnselected(p0: TabLayout.Tab?) {
        }

        override fun onTabSelected(p0: TabLayout.Tab?) {
            viewPager?.currentItem = p0?.position!!
        }

    }
}
